package com.steg.tencrypt.Steg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class Steganography {
    final static String TAG = Steganography.class.getSimpleName();
    static Context context;

    public Steganography(Context context){
        Steganography.context = context;
    }




    public static String saveImage(Bitmap bitmap, String filename){

        String root = context.getExternalFilesDir(null).toString();
        File myDir = new File(root + "/Crypts");
        myDir.mkdirs();

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


    public static Bitmap getImage(Uri filePath) {

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
            assert parcelFileDescriptor != null;
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!image.isMutable()){
            image = image.copy(Bitmap.Config.ARGB_8888,true);
        }
        return image;
    }



}

