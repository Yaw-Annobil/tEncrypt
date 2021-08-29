package com.steg.tencrypt.utilities;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EncryptsMotor extends AndroidViewModel {
    EncryptRepository repository;
    String filePath;
    String textData;
    private final MutableLiveData<EncryptViewState> viewStates = new MutableLiveData<>();
    public EncryptsMotor(@NonNull Application application) {
        super(application);

        viewStates.setValue(new EncryptViewState(true,null,null));

        repository = EncryptRepository.get(application);

        Uri encrypt = repository.Encrypt(getFilePath(),getTextData());

        try{
            viewStates.postValue(new EncryptViewState(false,encrypt,null));

        }catch (Exception e){
            viewStates.postValue(new EncryptViewState(false,null,e));
        }
    }

    LiveData<EncryptViewState> getViewState(){
        return viewStates;
    }

    String setFilePath(String filePath){
        this.filePath = filePath;

        return filePath;
    }

    String setTextData(String textData){
        this.textData = textData;

        return textData;
    }

    String getFilePath(){
        return filePath;
    }

    String getTextData(){
        return textData;
    }






}
