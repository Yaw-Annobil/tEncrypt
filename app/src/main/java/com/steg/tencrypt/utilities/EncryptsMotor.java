package com.steg.tencrypt.utilities;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.steg.tencrypt.Steg.Steganography;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EncryptsMotor extends AndroidViewModel {
    EncryptRepository repository;
    Uri filePath;
    String TAG = EncryptsMotor.class.getSimpleName();
    String textData;
    private final MutableLiveData<EncryptDataState> dataStates = new MutableLiveData<>();
    private final MutableLiveData<EncryptViewState> viewStates = new MutableLiveData<>();
    private final MutableLiveData<DecryptViewState> decryptViewState = new MutableLiveData<>();
    public EncryptsMotor(@NonNull Application application) {
        super(application);

        dataStates.setValue(new EncryptDataState(null,null));

        repository = EncryptRepository.get(application);
    }

    public LiveData<EncryptViewState> getViewState(){
        return viewStates;
    }

    public LiveData<EncryptDataState> getDataState(){
        return dataStates;
    }

    public LiveData<DecryptViewState> getDecryptViewState(){
        return decryptViewState;
    }

    public void setFilePath(Uri filePath){
        this.filePath = filePath;
        Log.d(TAG, "setFilePath: "+filePath);
    }

    public void setTextData(String textData){
        this.textData = textData;
        Log.d(TAG, "setTextData: "+textData);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void encrypt() throws IOException {
        viewStates.setValue(new EncryptViewState(true,null,null));
        if(getFilePath() != null){
            String encrypt = repository.Encrypt(getBytes(getFilePath()),getTextData());
        try{
            viewStates.postValue(new EncryptViewState(false,encrypt,null));

        }catch (Exception e){
            viewStates.postValue(new EncryptViewState(false,null,e));
        }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void decrypt(){
        decryptViewState.setValue(new DecryptViewState(null,null,true));
        if(getFilePath() != null){
            String decrypt = repository.Decrypt(getBytes(getFilePath()));
            Log.d(TAG, "decrypt: "+decrypt);
        try{
        decryptViewState.postValue(new DecryptViewState( decrypt,null,false));
        }catch (Exception e){
            decryptViewState.postValue(new DecryptViewState(decrypt,e,false));
        }
        }
    }

    public byte[] getBytes(Uri filePath){
        Bitmap bitmap = Steganography.getImage(filePath);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

        return stream.toByteArray();
    }

    public Uri getFilePath(){
        return filePath;
    }

    public String getTextData(){
        return textData;
    }






}
