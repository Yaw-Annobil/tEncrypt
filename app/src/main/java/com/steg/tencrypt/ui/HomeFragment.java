package com.steg.tencrypt.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.steg.tencrypt.R;
import com.steg.tencrypt.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * setting click listener on the masked card to navigate to its corresponding fragment
         */

        binding.encrypt.setOnClickListener(v-> NavHostFragment.findNavController(this)
                .navigate(HomeFragmentDirections.encrypt()));

        binding.set.setOnClickListener(v-> NavHostFragment.findNavController(this).
                navigate(HomeFragmentDirections.set()));

        binding.decrypt.setOnClickListener(v-> NavHostFragment.findNavController(this)
                .navigate(HomeFragmentDirections.decrypt("")));

        binding.template.setOnClickListener(v-> NavHostFragment.findNavController(this)
                .navigate(HomeFragmentDirections.template()));

        binding.history.setOnClickListener(v-> NavHostFragment.findNavController(this)
                .navigate(HomeFragmentDirections.history()));


    }



}
