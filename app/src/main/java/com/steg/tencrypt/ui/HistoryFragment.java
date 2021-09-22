package com.steg.tencrypt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.steg.tencrypt.databinding.FragmentHistoryBinding;
import com.steg.tencrypt.utilities.CryptAdapter;
import com.steg.tencrypt.utilities.CryptState;
import com.steg.tencrypt.utilities.EncryptsMotor;

public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;
    EncryptsMotor motor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentHistoryBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        motor = new ViewModelProvider(this).get(EncryptsMotor.class);

        final CryptAdapter adapter = new CryptAdapter(getLayoutInflater(),
                requireContext(),
                this::navTo);

        binding.history.setAdapter(adapter);

        motor.states.observe(getViewLifecycleOwner(), state ->{
            adapter.submitList(state.content);
        });

    }

    void navTo(CryptState state){
        NavHostFragment.findNavController(this)
                .navigate(HistoryFragmentDirections.fullscreen(state.filePath.getPath(),state.textData));
    }
}
