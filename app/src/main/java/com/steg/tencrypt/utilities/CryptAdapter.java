package com.steg.tencrypt.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.squareup.picasso.Picasso;
import com.steg.tencrypt.databinding.CryptBinding;

public class CryptAdapter extends ListAdapter<CryptState, CryptHolder>{
        final private LayoutInflater inflater;
        private final  CryptHolder.OnCryptListener listener;
        private final Context context;

        CryptAdapter(LayoutInflater inflater, Context context,
        CryptHolder.OnCryptListener listener){
            super(CryptState.DIFF_UTIL);
            this.inflater = inflater;
            this.listener = listener;
            this.context = context;

        }

    @NonNull
    @Override
    public CryptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CryptBinding binding = CryptBinding.inflate(inflater, parent, false);
        return new CryptHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptHolder holder, int position) {
            holder.bind(getItem(position));

    }
}
