package com.steg.tencrypt.Steg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;



public class Steganography {
    final static String TAG = Steganography.class.getSimpleName();
    Context context;

    public Steganography(Context context){
        this.context = context;
    }


    /**
     *Sterganography constructor
     */

    public String encode(Uri filePath, String textData) {
        File file = new File(String.valueOf(filePath));

        Bitmap copyImage = getImage(filePath);
        Bitmap newImage = addTextToImage(copyImage,textData);

       String newFileName = "Encrypted_" + new File(String.valueOf(filePath)).getName();

        return saveImage(newImage,newFileName);


    }

    /**
     * This methode extracts the hidden text in the image file
     * @param filePath is the filepath of the selected image
     * @return return the extracted text data
     */
    public String decode(Uri filePath){

        byte[] decode = new byte[0];
        Bitmap image = getImage(filePath);
        try{
            decode = decodeText(getByteData(image));

            return new String(decode);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new String(decode);
    }





    String saveImage(Bitmap bitmap, String filename){

        String root = context.getExternalFilesDir(null).toString();
        File myDir = new File(root + "/Crypts");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        File file = new File(myDir, filename+".png");
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * get the bitmap of the image using its path
     */


    Bitmap getImage(Uri filePath) {

        ParcelFileDescriptor parcelFileDescriptor =
                null;
        try {
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(filePath, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileDescriptor fileDescriptor = null;
        if (parcelFileDescriptor != null) {
            fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        }
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

        try {
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!image.isMutable()){
            image = convertToMutable(image);
        }
        return image;
    }

    Bitmap addTextToImage(Bitmap image, String text){

        //covert text and image into byte arrays
        byte[] img = getByteData(image);
        byte[] txt = text.getBytes();

        byte[] len = bitConversion(text.length());

        try {
            encodeText(img,len,0);
            encodeText(img,txt,32);
        }catch (Exception e){

        }

        return image;
    }

    /**
     *
     * @param image is the bitmap of the image to convert into bytes
     * @return the byte array of the image
     */
    byte[] getByteData(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    byte[] bitConversion(int i){
        //only using 4 bytes
        byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
        byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
        byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
        byte byte0 = (byte)((i & 0x000000FF)	   );

        return(new byte[]{byte3,byte2,byte1,byte0});
    }

    void encodeText(byte[] image, byte[] addition, int offset){

        //check if the data + offset will fit in the image
        if(addition.length + offset > image.length){
            throw new IllegalArgumentException("File not large enough");
        }

        //loop through each additional byte

        for (int i = 0; i < addition.length; ++i){
            int add = addition[i];
            for(int bit=7; bit>=0; --bit, ++offset) //ensure the new offset value carries on through both loops
            {
                //assign an integer to b, shifted by bit spaces AND 1
                //a single bit of the current byte
                int b = (add >>> bit) & 1;
                //assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
                //changes the last bit of the byte in the image to be the bit of addition
                image[offset] = (byte)((image[offset] & 0xFE) | b );
            }
        }
    }

    byte[] decodeText(byte[] image){
        int length = 0;
        int offset  = 32;
        //loop through 32 bytes of data to determine text length
        for(int i=0; i<32; ++i) //i=24 will also work, as only the 4th byte contains real data
        {
            length = (length << 1) | (image[i] & 1);
        }

        byte[] result = new byte[length];

        //loop through each byte of text
        for(int b=0; b<result.length; ++b )
        {
            //loop through each bit within a byte of text
            for(int i=0; i<8; ++i, ++offset)
            {
                //assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
                result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
            }
        }
        return result;
    }

    public String decodeMessage(Uri filePath){
        Bitmap image = getImage(filePath);
        StringBuilder stringBuilder = new StringBuilder();

        for (int x =0 ; x < image.getWidth(); x++){
            for (int y = 0; y < image.getHeight(); y++) {
                Color c = null;
                byte r = 0;
                byte g = 0;
                byte b = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    c = image.getColor(x, y);

                    r = (byte) c.red();
                    g = (byte) c.green();
                    b = (byte) c.blue();
                }



                byte[] RGB = {r, g, b};

                for (int i = 0; i <3; i++){
                    if((RGB[i] & 1) == 1){
                        stringBuilder.append("1");
                    }else{
                        stringBuilder.append("0");
                    }
                }


            }
        }

        Log.d(TAG, "decodeMessage: "+stringBuilder.toString());
        return stringBuilder.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getMessage(String encoded){
        int count = encoded.length()-1;

        StringBuilder message = new StringBuilder();

        int values = encoded.length()/8;
        byte[] ba = new byte[values];
        int arrayCount = values-1;
        while (arrayCount > 0) {
            StringBuilder bits = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                bits.insert(0,encoded.charAt(count-i));
            }
            byte b = (byte) Integer.parseInt(bits.toString(), 2);
            int x = Byte.toUnsignedInt(b);
            ba[arrayCount] = (byte) x;
            char c = (char) x;
            message.insert(0,c);

            count = count - 8;
            arrayCount--;

        }

        return new String(ba);


    }

    String encodeData(String message){
        String bitString = new BigInteger(message.getBytes()).toString(2);

        if(bitString.length() %8 != 0){
            String zero = "";
            while ((bitString.length() + zero.length()) % 8 != 0){
                zero = zero+"0";
            }

            bitString = zero+bitString;
        }

        Log.d(TAG, "encodeData: "+bitString);

        return bitString;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encodeImage(Uri filePath, String message){
        Bitmap image = getImage(filePath);

        int pointer = encodeData(message).length() -1;

        for (int i = image.getWidth()-1; i >=0; i--) {
            for (int j = image.getHeight()-1; j >=0; j--){
//                Color c = image.getColor(i,j);
                int color = image.getPixel(i,j);
//                byte r = (byte) c.red();
//                byte g = (byte) c.green();
//                byte b = (byte) c.blue();
                int a = Color.alpha(color);

                int pixel = image.getPixel(i,j);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                byte[] RGB = {(byte) redValue, (byte) greenValue, (byte) blueValue};

                Log.d(TAG, "encodeImage: "+RGB);

                byte[] newRGB = new byte[3];

                for (int k = 2; k >= 0; k--){
                    if(pointer >= 0){
                        int lsb;

                        if((RGB[k] & 1) == 1){
                            lsb =1;
                        }else{
                            lsb =0;
                        }

                        if(Character.getNumericValue(message.charAt(pointer)) != lsb){
                            if (lsb == 1){
                                newRGB[k] = (byte) (RGB[k] & ~(1));
                            }else{
                                newRGB[k] = (byte) (RGB[k] | 1);
                            }
                        }else{
                            newRGB[k] = RGB[k];

                        }

                    }
                    else {
                        newRGB[k] = (byte) (RGB[k] & ~(1));
                    }
                    pointer--;
                }

//                Color newColor = new Color(Color.argb(1,);
                int colors= Color.argb(a,Byte.toUnsignedInt(newRGB[0]),Byte.toUnsignedInt(newRGB[1]),Byte.toUnsignedInt(newRGB[2]));
                image.setPixel(i,j,colors);
            }

        }

        String newFileName = "Encrypted_" + new File(String.valueOf(filePath)).getName();

        return saveImage(image,newFileName);
    }

    /**
     * Max length to attempt to decode.
     */
    private static final int MAX_DECODABLE_LENGTH = 0x00FFFFFF;

    private static final int LSB = 1;

    /**
     * Breaks down message into bits. Each bit replaces the LSB of a pixel.
     * The minimum number of pixels is 32 + (message.length*8), as the length of the message is
     * also encoded into the pixels, and each bit requires one pixel.
     * @param pixels Pixels as (A)RGB integers: R=0xFF0000, G=0x00FF00, B=0x0000FF
     * @param message Message to encode.
     * @return Pixels encoded with message.
     */
    public static int[] encode(int[] pixels, String message) {
        Log.d("Steganography.Encode", "Encode Begin");
        byte[] data = message.getBytes();

        //Insert length into data
        {
            byte[] dataWithLength = new byte[4 + data.length];

            int dataLength = data.length;
            for (int i = 0; i < 4; i++) {
                dataWithLength[i] = (byte)(dataLength & 0xff);
                dataLength >>>= 8;
            }

            System.arraycopy(data, 0, dataWithLength, 4, data.length);
            data = dataWithLength;
        }

        int pixelIndex = 0;

        //For each byte of data, insert its 8 bits into the LSB of 8 pixels.
        for (byte b : data) {
            for (int i = 0; i < 8; i++, pixelIndex++) {
                //Remove LSB from pixel
                pixels[pixelIndex] &= ~LSB;
                //OR LSB of byte into pixel
                pixels[pixelIndex] |= b & LSB;
                //Bit-shift byte into next position
                b >>>= 1;
            }
        }

        Log.d("Steganography.Encode", "Encode End");

        return pixels;
    }
    /**
     * Decodes data out of pixels, and constructs a String out of it. .
     * Each byte of data is constructed out of the LSB of 8 consecutive pixels.
     * @param pixels Pixels as (A)RGB integers: R=0xFF0000, G=0x00FF00, B=0x0000FF
     * @return Decoded data.
     */
    public static String decode(int[] pixels) {
        Log.d("Steganography.Decode", "Decode Begin");

        int pixelIndex = 0;

        //Decode length;
        int length = decodeBitsFromPixels(pixels, 32, pixelIndex);
        pixelIndex += 32;

        if (length < 0 || length > MAX_DECODABLE_LENGTH) {
            throw new IllegalArgumentException("Failed to decode. Are you sure the image is encoded?");
        }

        byte[] data = new byte[length];

        for (int byteIndex = 0; byteIndex < length; byteIndex++, pixelIndex+=8) {
            data[byteIndex] = (byte) decodeBitsFromPixels(pixels, 8, pixelIndex);
        }

        Log.d("Steganography.Decode", "Decode End");

        return new String(data);
    }

    /**
     * Decodes specified number of bits out of pixels, returns them as a single integer.
     * @param pixels Pixels as (A)RGB integers: R=0xFF0000, G=0x00FF00, B=0x0000FF
     * @param numberOfBits Number of bits to decode.
     * @param pixelIndex Index to start at.
     * @return Decoded integer.
     */
    private static int decodeBitsFromPixels(int[] pixels,
                                            int numberOfBits,
                                            int pixelIndex) {

        int decodedValue = 0;

        for (int i = 0; i < numberOfBits; i++, pixelIndex++) {
            //Get bit
            int bit = pixels[pixelIndex] & LSB;
            //Shift into position
            decodedValue |= bit << i;
        }

        return decodedValue;

    }

    public String encoded(Uri filePath, String message){
        Bitmap bitmap = getImage(filePath);

        int width = bitmap.getWidth();

        int height = bitmap.getHeight();

        int numberOfPixels = width * height;

        byte[] data = message.getBytes();

        int requiredLength = data.length * 8+32;

        if(requiredLength > numberOfPixels){
            throw new IllegalArgumentException("Message Too Long to fit in image");
        }

        int[] encodedPixels = encode(getPixels(bitmap,requiredLength),message);

        setPixels(bitmap,encodedPixels);

        Log.d(TAG, "encoded: sucessfull");

        return saveImage(bitmap,"Encrypted_"+new Random().nextInt());
    }

    /**
     * Gets the area required for numberOfBytes to fit into an image width width of imageWidth.
     * @param requiredLength Number of pixels required for message to fit in image.
     * @param imageWidth Width of image.
     * @return [width, height]
     */
    private static int[] getMinimumAreaBounds(int requiredLength, int imageWidth) {
        if (requiredLength < imageWidth) {
            return new int[] {requiredLength, 1};
        } else {
            return new int[] {imageWidth, (int) Math.ceil(((double) requiredLength) / ((double) imageWidth)) };
        }
    }

    public static void setPixels(Bitmap bitmap, int[] pixels) {
        int[] bounds = getMinimumAreaBounds(pixels.length, bitmap.getWidth());
        bitmap.setPixels(pixels, 0, bounds[0], 0, 0, bounds[0], bounds[1]);
    }

    public static int[] getPixels(Bitmap bitmap, int requiredLength) {
        int[] bounds = getMinimumAreaBounds(requiredLength, bitmap.getWidth());

        int[] pixels = new int[bounds[0] * bounds[1]];
        bitmap.getPixels(pixels, 0, bounds[0], 0, 0, bounds[0], bounds[1]);

        return pixels;
    }

    /**
     * Bitmaps must be mutable in order for setPixels to works.
     * http://stackoverflow.com/a/9194259
     * <p>
     * Converts a immutable bitmap to a mutable bitmap. This operation doesn't allocates
     * more memory that there is already allocated.
     *
     * @param imgIn - Source image. It will be released, and should not be used more
     * @return a copy of imgIn, but muttable.
     */
    private static Bitmap convertToMutable(Bitmap imgIn) {
        try {
            //this is the file going to use temporally to save the bytes.
            // This file will not be a image, it will store the raw image data.
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

            //Open an RandomAccessFile
            //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
            //into AndroidManifest.xml file
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            // get the width and height of the source bitmap.
            int width = imgIn.getWidth();
            int height = imgIn.getHeight();
            Bitmap.Config type = imgIn.getConfig();

            //Copy the byte to the file
            //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, (long) imgIn.getRowBytes() * height);
            imgIn.copyPixelsToBuffer(map);
            //recycle the source bitmap, this will be no longer used.
            imgIn.recycle();
            System.gc();// try to force the bytes from the imgIn to be released

            //Create a new bitmap to load the bitmap again. Probably the memory will be available.
            imgIn = Bitmap.createBitmap(width, height, type);
            map.position(0);
            //load it back from temporary
            imgIn.copyPixelsFromBuffer(map);
            //close the temporary file and channel , then delete that also
            channel.close();
            randomAccessFile.close();

            // delete the temp file
            file.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgIn;
    }


}

