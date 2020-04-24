package com.example.timetime.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.timetime.database.entity.Color;

import java.util.List;

@Dao
public interface ColorsDao {
    @Query("SELECT * FROM Color")
    List<Color> getAll();

    @Query("TRUNCATE TABLE color_table")
    void resetTable();

    @Insert
    void insertAll(Color... colors);
}

