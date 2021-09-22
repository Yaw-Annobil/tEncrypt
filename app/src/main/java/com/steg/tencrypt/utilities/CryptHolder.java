package com.steg.tencrypt.utilities;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.steg.tencrypt.databinding.CryptBinding;

public class CryptHolder extends RecyclerView.ViewHolder {

    private static final String TAG = CryptHolder.class.getSimpleName();
    private CryptBinding binding;

    public interface OnCryptListener{
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

//        Picasso.get().load(new File(state.filePath.getPath())).fit().into(binding.image);

        Log.d(TAG, "bind: "+state.filePath);


        binding.setTextData(state.textData);
        binding.setType(state.type);

        binding.executePendingBindings();
    }
}
