package com.steg.tencrypt.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kotlin.text.UStringsKt;

@Database(entities = CryptEntity.class, version = 0, exportSchema = false)
abstract class CryptDatabase extends RoomDatabase {


    public static final String DB_NAME = "CryptDatabase";
    public static volatile CryptDatabase INSTANCE;

    synchronized static CryptDatabase get(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, CryptDatabase.class, DB_NAME).build();

        }
        return INSTANCE;
    }
    abstract CryptStore store();
}