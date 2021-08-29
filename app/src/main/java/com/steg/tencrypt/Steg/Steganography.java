package com.steg.tencrypt.Steg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;



public class Steganography {

    Context context;

    public Steganography(Context context){
        this.context = context;
    }


    /**
     *Sterganography constructor
     */

    public Uri encode(String filePath, String textData) {
        File file = new File(filePath);

        Bitmap copyImage = getImage(filePath);
        Bitmap newImage = addTextToImage(copyImage,textData);

       String newFileName = filePath.split(".")[-1];

        return saveImage(newImage,newFileName);


    }

    /**
     * This methode extracts the hidden text in the image file
     * @param filePath is the filepath of the selected image
     * @return return the extracted text data
     */
    String decode(String filePath){

        byte[] decode;
        Bitmap image = getImage(filePath);
        try{
            decode = decodeText(getByteData(image));

            return new String(decode);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }





    Uri saveImage(Bitmap bitmap,String filename){

        String root = context.getExternalFilesDir(null).toString();
        File myDir = new File(root + "/Crypts");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        File file = new File(myDir, filename);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }

    /**
     * get the bitmap of the image using its path
     */


    Bitmap getImage(String filePath){
        File file = new File(filePath);

        final Bitmap[] mbitmap = {null};

        Picasso.get().load(file).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mbitmap[0] = bitmap;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        return mbitmap[0];
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

}

