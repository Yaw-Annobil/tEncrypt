package com.steg.tencrypt.utilities;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.steg.tencrypt.Steg.Steganography;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * It is responsible for encrypting the textData into the image file
 */
class EncryptRepository {
    static volatile EncryptRepository INSTANCE;
    Steganography steganography;
    Encode encoded;
    String root;
    static String TAG = EncryptRepository.class.getSimpleName();
    Python py;

    public final Map<String, CryptModel> cryptItems = new HashMap<>();




    synchronized static EncryptRepository get(Context context){
        if(INSTANCE == null){
            INSTANCE = new EncryptRepository(context.getApplicationContext());
        }

        return INSTANCE;

    }



    public EncryptRepository(Context applicationContext) {
        encoded = new Encode(applicationContext);
        steganography = new Steganography(applicationContext);
        Python.start(new AndroidPlatform(applicationContext));
        py = Python.getInstance();
        root = applicationContext.getExternalFilesDir(null).toString();

    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    String Encrypt(Uri filePath, String textData) throws IOException {
//        return encoded.encode(filePath,textData);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    String Decrypt(Uri filePath){
//        return steganography.decodedValue(filePath);
//    }

    String Encrypt(byte[] data, String textData) throws IOException{
        PyObject encode = py.getModule("encode");

        PyObject en = encode.callAttr("encode",data,textData);

        Log.d(TAG, "Encrypt: Successful");

        BitmapFactory.Options options = new BitmapFactory.Options();


        byte[] value = en.toJava(byte[].class);


        Bitmap bitmap = BitmapFactory.decodeByteArray(value,0, value.length, options);

        Log.d(TAG, "Encrypt: saved");

        return Steganography.saveImage(bitmap,"Encrypted_"+new Random().nextInt() );
    }

    String Decrypt(byte[] value){
        PyObject decodedValue = py.getModule("decryption");

        PyObject decodeMessage = decodedValue.callAttr("decode",value);

        String message = decodeMessage.toString();

        Log.d(TAG, "Decrypt: "+message);

        return message;
    }

}
