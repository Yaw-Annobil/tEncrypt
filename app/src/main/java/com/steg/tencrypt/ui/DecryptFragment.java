package com.steg.tencrypt.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.steg.tencrypt.R;
import com.steg.tencrypt.databinding.FragmentDecryptBinding;
import com.steg.tencrypt.utilities.EncryptsMotor;

public class DecryptFragment extends Fragment {
    FragmentDecryptBinding binding;
    EncryptsMotor motor;
    private Uri selectedImageUri;
    static final String TAG = DecryptFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentDecryptBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        motor = new ViewModelProvider(this).get(EncryptsMotor.class);
        binding.selectImage.setOnClickListener(this::chooser);
        binding.decrypt.setOnClickListener(this::decrypt);
        binding.decryptText.setOnClickListener(this::fullscreen);


    }

    void fullscreen(View v){
        if (!TextUtils.isEmpty(binding.decryptText.getText())){
            NavHostFragment.findNavController(this).navigate(DecryptFragmentDirections
                    .fullscreen(selectedImageUri.toString(), motor.getTextData()));
        }
    }

    void decrypt(View v){
        binding.progressCircular.setVisibility(View.VISIBLE);
        motor.setFilePath(selectedImageUri);

        new Handler().postDelayed(() -> motor.decrypt(),1000);

        motor.getDecryptViewState().observe(requireActivity(), decryptViewState -> {
            if(decryptViewState.isLoading){
                binding.progressCircular.setVisibility(View.VISIBLE);
            }else{
                binding.progressCircular.setVisibility(View.GONE);
            }
            if(decryptViewState.throwable == null){
                binding.decryptText.setText(decryptViewState.textData);
                motor.save(motor.getFilePath(),decryptViewState.textData,getString(R.string.decryption));
                motor.getSaveEvents().observe(requireActivity(), event -> event.handle(result1 -> {
                    String message;

                    if (result1.throwable == null) {
                        message = result1.content.filePath + " was saved!";
                    }
                    else {
                        message = result1.throwable.getLocalizedMessage();
                    }

                    Log.d(TAG, "onViewCreated: "+message);
                }));

            }else{
                binding.progressCircular.setVisibility(View.GONE);
                binding.decryptText.setTextColor(requireContext()
                        .getColor(android.R.color.holo_red_light));
                binding.decryptText.setText(decryptViewState.throwable.toString());
            }
        });
    }

    void chooser(View v){
        getContent.launch("image/*");
    }



    ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(Uri result) {
                    Log.d(TAG, "onActivityResult: " + result);
                    binding.selectedImage.setImageURI(result);
                    selectedImageUri = result;
                }
            });
}
