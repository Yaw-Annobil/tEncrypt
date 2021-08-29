package com.steg.tencrypt.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

<<<<<<< HEAD
=======
import kotlin.text.UStringsKt;

>>>>>>> b55ae959a9b61ca00ea3a27558a857c72468219d
@Database(entities = CryptEntity.class, version = 0, exportSchema = false)
abstract class CryptDatabase extends RoomDatabase {


<<<<<<< HEAD
    public static final String DB_NAME = "cryptdatabase";
=======
    public static final String DB_NAME = "CryptDatabase";
>>>>>>> b55ae959a9b61ca00ea3a27558a857c72468219d
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