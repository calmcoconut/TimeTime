package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.timetime.database.entity.Icon;

import java.util.List;

@Dao
public interface IconDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Icon icon);

    @Query("DELETE FROM icon_table")
    void deleteAll();

    @Query("SELECT * FROM icon_table")
    LiveData<List<Icon>> getAllIcons();
}
