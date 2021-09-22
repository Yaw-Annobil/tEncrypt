package com.steg.tencrypt.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = CryptEntity.class, version = 1, exportSchema = false)
public abstract class CryptDatabase extends RoomDatabase {

    /**
     * initializing the database
     */
    public static final String DB_NAME = "cryptdatabase";
public static volatile CryptDatabase INSTANCE;

public synchronized static CryptDatabase get(Context context) {
    if (INSTANCE == null) {
        INSTANCE =
                Room.databaseBuilder(context, CryptDatabase.class, DB_NAME).build();

    }
    return INSTANCE;
}
    public abstract CryptStore store();
}