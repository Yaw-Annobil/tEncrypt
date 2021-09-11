package com.steg.tencrypt.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.steg.tencrypt.Steg.Steganography;

import java.io.IOException;

public class Encode {
    Context context;
    final static String TAG = Encode.class.getSimpleName();
    public Encode(Context context){
        this.context = context;
    }
    public String encode(Uri filePath, String message) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),filePath);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int numberOfPixels = w*h;

        byte[] data = message.getBytes();

        int requiredLength = data.length * 8 + 32;

        if (requiredLength > numberOfPixels) {
            throw new IllegalArgumentException("Message is too long to fit into pixels.");
        }

        int[] encodedPixels = StegUtils.encode(
                BitmapUtils.getPixels(bitmap, requiredLength),
                message
        );

        BitmapUtils.setPixel(bitmap, encodedPixels);



//        Uri resultUri = FileUtils.saveBitmap(bitmap);

        Log.d(TAG, "encode: encoding successful");

        return Steganography.saveImage(bitmap,"Encrypted");

    }

}
