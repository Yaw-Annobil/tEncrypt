package com.steg.tencrypt.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CryptEntity {
    @PrimaryKey
    public int id;

    @NonNull
    public String filePath;
    public String textData;

}
