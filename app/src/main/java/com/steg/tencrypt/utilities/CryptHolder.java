package com.steg.tencrypt.utilities;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.steg.tencrypt.databinding.CryptBinding;

public class CryptHolder extends RecyclerView.ViewHolder {

    private CryptBinding binding;

    interface OnCryptListener{
        void onCryptClick(CryptState state);
    }

    public CryptHolder(CryptBinding binding, OnCryptListener listener) {
        super(binding.getRoot());

        this.binding = binding;

        binding.setClickListener(v->{
            listener.onCryptClick(binding.getState());
        });
    }

    void bind(CryptState state){
        binding.setState(state);

        binding.setImageUrl(state.filePath);
        binding.setTextData(state.textData);

        binding.executePendingBindings();
    }
}
