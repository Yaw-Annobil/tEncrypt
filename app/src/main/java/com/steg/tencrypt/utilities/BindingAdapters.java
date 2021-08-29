package com.steg.tencrypt.utilities;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.io.File;

public class BindingAdapters {
    @BindingAdapter("imageurl")
    public static void bindimageurl(ImageView imageview, String url){
        if (url != null) {
            File file = new File(url);
            Picasso.get().load(file).into(imageview);
        }
    }
}
