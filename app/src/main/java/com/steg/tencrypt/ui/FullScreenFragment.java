package com.steg.tencrypt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.steg.tencrypt.databinding.FragmentPreviewBinding;

public class FullScreenFragment extends Fragment {
    FragmentPreviewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPreviewBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String text = FullScreenFragmentArgs.fromBundle(getArguments()).getTextData();
        binding.fullText.setEnabled(false);
        binding.fullText.setText(text);
    }
}
