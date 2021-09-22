package com.steg.tencrypt.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * The Dao is similar to API calls into the data
 * it provides method to delete, update, query etc for the database
 */
@Dao
public
interface CryptStore {
    @Query("SELECT * FROM CryptEntity ORDER BY dateAdded")
    LiveData<List<CryptEntity>> all();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(CryptEntity entity);



}
