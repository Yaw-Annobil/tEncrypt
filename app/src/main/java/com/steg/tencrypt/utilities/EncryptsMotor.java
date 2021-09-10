package com.steg.tencrypt.utilities;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EncryptsMotor extends AndroidViewModel {
    EncryptRepository repository;
    Uri filePath;
    String TAG = EncryptsMotor.class.getSimpleName();
    String textData;
    private final MutableLiveData<EncryptDataState> dataStates = new MutableLiveData<>();
    private final MutableLiveData<EncryptViewState> viewStates = new MutableLiveData<>();
    public EncryptsMotor(@NonNull Application application) {
        super(application);

        dataStates.setValue(new EncryptDataState(null,null));

        viewStates.setValue(new EncryptViewState(true,null,null));

        repository = EncryptRepository.get(application);
    }

    public LiveData<EncryptViewState> getViewState(){
        return viewStates;
    }

    public LiveData<EncryptDataState> getDataState(){
        return dataStates;
    }

    public void setFilePath(Uri filePath){
        this.filePath = filePath;
        Log.d(TAG, "setFilePath: "+filePath);
    }

    public void setTextData(String textData){
        this.textData = textData;
        Log.d(TAG, "setTextData: "+textData);
    }

    public void encrypt(Uri filePath, String textData){
        Uri encrypt = repository.Encrypt(filePath,textData);
        try{
            viewStates.postValue(new EncryptViewState(false,encrypt,null));

        }catch (Exception e){
            viewStates.postValue(new EncryptViewState(false,null,e));
        }
    }

    public Uri getFilePath(){
        return filePath;
    }

    public String getTextData(){
        return textData;
    }






}
