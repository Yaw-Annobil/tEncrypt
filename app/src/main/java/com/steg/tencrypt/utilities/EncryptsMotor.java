package com.steg.tencrypt.utilities;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EncryptsMotor extends AndroidViewModel {
    EncryptRepository repository;
    Uri filePath;
    String textData;
    private final MutableLiveData<EncryptViewState> viewStates = new MutableLiveData<>();
    public EncryptsMotor(@NonNull Application application) {
        super(application);

        viewStates.setValue(new EncryptViewState(true,null,null));

        repository = EncryptRepository.get(application);


    }

    public LiveData<EncryptViewState> getViewState(){
        return viewStates;
    }

    public Uri setFilePath(Uri filePath){
        this.filePath = filePath;

        return filePath;
    }

    String setTextData(String textData){
        this.textData = textData;

        return textData;
    }

    public void encrypt(){
        Uri encrypt = repository.Encrypt(getFilePath(),getTextData());
        viewStates.setValue(new EncryptViewState(true,null,null));

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