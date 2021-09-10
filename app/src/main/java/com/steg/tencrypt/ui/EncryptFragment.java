package com.steg.tencrypt.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.steg.tencrypt.R;
import com.steg.tencrypt.databinding.FragmentEncryptBinding;
import com.steg.tencrypt.utilities.EncryptsMotor;

public class EncryptFragment extends Fragment {
    private static final String TAG = EncryptFragment.class.getSimpleName();
    FragmentEncryptBinding binding;
    EncryptsMotor motor;
    Uri EncryptedUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentEncryptBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize the motor
        motor = new ViewModelProvider(this).get(EncryptsMotor.class);

        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                motor.setTextData(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.selectImage.setOnClickListener(this::chooser);
        binding.done.setOnClickListener(this::encrypt);
    }

    //method to encrypt text in image on click
    void encrypt(View view){
        if (validateInputData()){
            motor.encrypt();
            motor.getViewState().observe(this, encryptViewState -> {
                binding.progressCircular.setVisibility(encryptViewState.isLoading?View.VISIBLE:View.GONE);

                if(!encryptViewState.isLoading && encryptViewState.error == null){
                    binding.share.setVisibility(View.VISIBLE);
                    EncryptedUri = encryptViewState.uri;
                }
                else if (encryptViewState.error != null){
                    Snackbar.make(requireView(),String.valueOf(encryptViewState.error),Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    //method to select an image from phone
    void chooser(View v){
        getContent.launch("image/*");
    }

    //validate if all inputs have been entered
    boolean validateInputData(){
        if (!TextUtils.isEmpty(motor.getTextData()) && !TextUtils.isEmpty(String.valueOf(motor.getFilePath()))){
            Snackbar.make(requireView(), requireContext().getString(R.string.enter_neccessaru_fields),Snackbar.LENGTH_LONG).show();
        }
        else if (!TextUtils.isEmpty(motor.getTextData())){
            Snackbar.make(requireView(), requireContext().getString(R.string.enter_text_data),Snackbar.LENGTH_LONG).show();
        }
        else if (!TextUtils.isEmpty(String.valueOf(motor.getFilePath()))){
            Snackbar.make(requireView(), requireContext().getString(R.string.image_ecrypt),Snackbar.LENGTH_LONG).show();
        }
        else {
            return true;
        }

        return false;
    }

    ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    Log.d(TAG, "onActivityResult: "+result);
                    motor.setFilePath(result);
                }
            });

}
