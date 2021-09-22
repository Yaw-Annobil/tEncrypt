package com.steg.tencrypt.utilities;

import android.net.Uri;

import com.steg.tencrypt.db.CryptEntity;

/**
 * this is the model class of our recyclerview
 */
public class CryptModel {
    /**
     * This string is the path of the encrypted image file
     */
    public final Uri filePath;

    /**
     * textdata is the textdata either extracted or created
     */
    final String textData;

    /**
     * @type is to check
     * whether the data was saved from encrypting or decrypting
     */
    final String type;

    final long dateAdded;

    /**
     *
     * @param entity is the row entry with the corresponding columns
     *
     */

    public CryptModel(CryptEntity entity) {
        this.filePath = entity.filePath;
        this.textData = entity.textData;
        this.dateAdded = entity.dateAdded;
        this.type = entity.type;
    }
}
