package com.steg.tencrypt.utilities;

/**
 * this is the model class of our recyclerview
 */
public class CryptModel {
    /**
     * This string is the path of the image file
     */
    final String filePath;

    /**
     * textdata is the textdata either extracted or created
     */

    final String textData;

    public CryptModel(String filePath, String textData) {
        this.filePath = filePath;
        this.textData = textData;
    }
}
