package com.steg.tencrypt.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

public class Encode {
    Context context;
    final static String TAG = Encode.class.getSimpleName();
    public Encode(Context context){
        this.context = context;
    }
    public Uri encode(Uri filePath, String message){
        Bitmap bitmap = BitmapUtils.decodeFile(FileUtils.uriToFilePath(context,filePath));
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

        BitmapUtils.setPixels(bitmap, encodedPixels);

        Uri resultUri = FileUtils.saveBitmap(bitmap);

        Log.d(TAG, "encode: encoding successful");

        return resultUri;

    }

}
