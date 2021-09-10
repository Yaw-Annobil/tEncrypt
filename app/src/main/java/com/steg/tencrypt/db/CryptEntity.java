package com.steg.tencrypt.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CryptEntity {
    @PrimaryKey
    @NonNull
    public String filePath;

    public String textData;

}
