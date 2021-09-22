package com.steg.tencrypt.utilities;

import android.annotation.SuppressLint;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class CryptState {
    /**
     * This string is the path of the image file
     */
    final Uri filePath;

    /**
     * textdata is the textdata either extracted or created
     */

    final String textData;

    public final long dateAdded;

    public  final String type;

    public CryptState(CryptModel model) {
        this.filePath = model.filePath;
        this.textData = model.textData;
        this.dateAdded = model.dateAdded;
        this.type = model.type;
    }
    public static DiffUtil.ItemCallback<CryptState> DIFF_UTIL = new DiffUtil.ItemCallback<CryptState>() {
        @Override
        public boolean areItemsTheSame(@NonNull CryptState oldItem, @NonNull CryptState newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull CryptState oldItem, @NonNull CryptState newItem) {
            return oldItem.filePath.equals(newItem.filePath);
        }

    };

}

