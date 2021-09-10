package com.steg.tencrypt.utilities;

import android.net.Uri;

public class EncryptDataState {
    public Uri filePath;

    public String textData;

    public EncryptDataState(Uri filePath, String textData) {
        this.filePath = filePath;
        this.textData = textData;
    }
}
