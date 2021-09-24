package com.steg.tencrypt.utilities;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.steg.tencrypt.Steg.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EncryptsMotor extends AndroidViewModel {
    CryptRepository repository;
    Uri filePath;
    String TAG = EncryptsMotor.class.getSimpleName();
    String textData;
    private final MutableLiveData<EncryptDataState> dataStates = new MutableLiveData<>();
    private final MutableLiveData<EncryptViewState> viewStates = new MutableLiveData<>();
    private final MutableLiveData<DecryptViewState> decryptViewState = new MutableLiveData<>();
    private MediatorLiveData<Event<CryptResult>> saveEvent = new MediatorLiveData<>();
    private LiveData<Event<CryptResult>> lastSave;
    public final LiveData<MainViewState> states;
    public EncryptsMotor(@NonNull Application application) {
        super(application);
        repository = CryptRepository.get(application);
        states = Transformations.map(repository.load(),
                models ->{
                    ArrayList<CryptState> content = new ArrayList<>();

                    for (CryptModel model: models){
                        content.add(new CryptState(model));
                    }
                    return new MainViewState(content);
                });
    }

    public LiveData<Event<CryptResult>> getSaveEvents(){
        return saveEvent;
    }


    public void save(Uri filePath, String textData, String type){
        saveEvent.removeSource(lastSave);
        lastSave = Transformations.map(repository.save(filePath,textData,type),Event::new);
        saveEvent.addSource(lastSave, event -> saveEvent.setValue(event));
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
        Bitmap bitmap = ImageUtils.getImage(filePath);

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
