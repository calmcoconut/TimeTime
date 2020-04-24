package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.timetime.database.entity.Color;

import java.util.List;

@Dao
public interface ColorDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Color color);

    @Query("DELETE FROM color_table")
    void deleteAll();

    @Query("SELECT * FROM color_table")
    LiveData<List<Color>> getAllColors();
}
