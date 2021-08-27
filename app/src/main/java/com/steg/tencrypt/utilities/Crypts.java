package com.steg.tencrypt.utilities;

public class Crypts {

    final String filePath;

    final String textData;

    final int timeInMilli;

    public Crypts(String filePath, String textData, int timeInMilli) {
        this.filePath = filePath;
        this.textData = textData;
        this.timeInMilli = timeInMilli;
    }
}
