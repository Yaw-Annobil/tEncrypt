package com.steg.tencrypt.utilities;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.steg.tencrypt.Steg.Steganography;
import com.steg.tencrypt.db.CryptDatabase;
import com.steg.tencrypt.db.CryptEntity;
import com.steg.tencrypt.db.CryptStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * It is responsible for encrypting the textData into the image file
 */
class EncryptRepository {
    static volatile EncryptRepository INSTANCE;
    Steganography steganography;
    String root;
    CryptDatabase db;
    static String TAG = EncryptRepository.class.getSimpleName();
    Python py;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public final Map<String, CryptModel> cryptItems = new HashMap<>();





    synchronized static EncryptRepository get(Context context){
        if(INSTANCE == null){
            INSTANCE = new EncryptRepository(context.getApplicationContext());
        }

        return INSTANCE;

    }



    public EncryptRepository(Context applicationContext) {
        db = CryptDatabase.get(applicationContext);
        steganography = new Steganography(applicationContext);
        Python.start(new AndroidPlatform(applicationContext));
        py = Python.getInstance();
        root = applicationContext.getExternalFilesDir(null).toString();

    }

    LiveData<CryptResult> save(Uri filePath, String text,String type){
        return new SaveLiveData(text,filePath,type,executor,db.store());
    }

    LiveData<List<CryptModel>> load(){
        return Transformations.map(db.store().all(), entities -> {
            ArrayList<CryptModel> result = new ArrayList<>();

            for (CryptEntity entity: entities){
                result.add(new CryptModel(entity));
            }

            return result;
        });
    }

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

    private static class SaveLiveData extends LiveData<CryptResult>{
        private final String textData;
        private final Uri filePath;
        private final CryptStore store;
        private final Executor executor;
        private final String type;

        SaveLiveData(String textData, Uri filePath,String type, Executor executor,CryptStore store){
            this.textData = textData;
            this.filePath = filePath;
            this.executor = executor;
            this.type = type;
            this.store = store;
        }

        @Override
        protected void onActive() {
            super.onActive();
            executor.execute(()->{
               try{
                   CryptEntity entity = new CryptEntity();
                   entity.dateAdded = System.currentTimeMillis();
                   entity.textData = textData;
                   entity.filePath = filePath;
                   entity.type = type;

                   store.save(entity);
                   postValue(new CryptResult(new CryptModel(entity),null));
               }catch (Throwable e){
                   postValue(new CryptResult(null,e));
               }
            });
        }
    }

}
