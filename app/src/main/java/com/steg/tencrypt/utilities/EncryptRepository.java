package com.steg.tencrypt.utilities;


import android.content.Context;
import android.net.Uri;

import com.steg.tencrypt.Steg.Steganography;

import java.util.HashMap;
import java.util.Map;

/**
 * It is responsible for encrypting the textData into the image file
 */
class EncryptRepository {
    static volatile EncryptRepository INSTANCE;
    Steganography steganography;

    public final Map<String, CryptModel> cryptItems = new HashMap<>();




    synchronized static EncryptRepository get(Context context){
        if(INSTANCE == null){
            INSTANCE = new EncryptRepository(context.getApplicationContext());
        }

        return INSTANCE;

    }



    public EncryptRepository(Context applicationContext) {
        steganography = new Steganography(applicationContext);
    }

    String Encrypt(Uri filePath, String textData){
        return steganography.encode(filePath,textData);
    }


}
