package com.example.timetime.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.timetime.database.entity.Colors;

import java.util.List;

@Dao
public interface ColorsDao {
    @Query("SELECT * FROM color_table")
    List<Colors> getAll();

    @Query("TRUNCATE TABLE color_table")
    void resetTable();

    @Insert
    void insertAll(Colors... colors);
}

