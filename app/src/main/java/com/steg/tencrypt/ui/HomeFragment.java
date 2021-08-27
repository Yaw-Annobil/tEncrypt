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

        List<View> views = new ArrayList<>();
        
        views.add(binding.encrypt);
        views.add(binding.decrypt);
        views.add(binding.history);
        views.add(binding.template);
        views.add(binding.set);

        for (View i : views) {
            i.setOnClickListener(this::navTo);
        }

    }

    @SuppressLint("NonConstantResourceId")
    void navTo(View view){
        int id = view.getId();

        switch(id){
            case R.id.encrypt:
                NavHostFragment.findNavController(this).
                        navigate(HomeFragmentDirections.encrypt());
            case R.id.decrypt:
                NavHostFragment.findNavController(this).
                        navigate(HomeFragmentDirections.decrypt(""));
            case R.id.history:
                NavHostFragment.findNavController(this).
                        navigate(HomeFragmentDirections.history());
            case R.id.template:
                NavHostFragment.findNavController(this).
                        navigate(HomeFragmentDirections.template());
            case R.id.set:
                NavHostFragment.findNavController(this).
                        navigate(HomeFragmentDirections.set());
            default:
                throw new IllegalArgumentException();
                
        }
    }
}
