package com.steg.tencrypt.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface CryptStore {
    @Query("SELECT * FROM CryptEntity")
    LiveData<List<CryptEntity>> all();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(CryptEntity entity);
}
