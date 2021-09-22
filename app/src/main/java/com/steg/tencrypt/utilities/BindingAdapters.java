package com.steg.tencrypt.utilities;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {


    @BindingAdapter("imageurl")
    public static void bindimageurl(ImageView imageView, Uri url){
        if(url != null){
            Picasso.get().load(url).into(imageView);
        }
    }
}
