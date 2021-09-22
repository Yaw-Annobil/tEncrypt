package com.steg.tencrypt.utilities;

import android.net.Uri;

import androidx.room.TypeConverter;

import java.io.File;

/**
 * This class converts uri into string to be saved in the database
 * and string to uri to be used
 */
public class UriToStringConverter {
    @TypeConverter
    public String fromUri(Uri uri){
        if(uri == null){
            return (null);
        }
        return uri.toString();
    }


    @TypeConverter
    public Uri toUri(String uriStr){
        if(uriStr == null){
            return (null);
        }

        File file = new File(uriStr);

        return Uri.fromFile(file);
    }
}
