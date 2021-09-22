package com.steg.tencrypt.db;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.steg.tencrypt.utilities.UriToStringConverter;


/**
 *
 * Entity is the table in the db
 */
@Entity
public class CryptEntity {
    /**
     * The file path has been
     * tagged primarykey so
     * that no double can be
     * saved in the same dir
     */
    @PrimaryKey
    @NonNull
    @TypeConverters(UriToStringConverter.class)
    public Uri filePath;

    /**
     * The text data stores text in the corresponding column
     */

    public String textData;

    /**
     * date added is the date the item was added
     */

    public long dateAdded;

    /**
     * column for type
     */
    public String type;


}
