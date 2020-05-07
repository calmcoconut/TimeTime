package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.timetime.database.entity.TimeLog;

import java.util.List;

@Dao
public interface TimeLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (TimeLog timeTracker);

    @Update
    void update (TimeLog timeTracker);

//    @Query("UPDATE timeLog_table SET activity_color = :newColor WHERE activity = :activityName")
//    void updateActivityColor(String activityName, String newColor);
//
//    @Query("UPDATE timeLog_table SET activity_icon = :newIcon WHERE activity = :activityName")
//    void updateActivityColor(String activityName, int newIcon);
//
//    @Query("UPDATE timeLog_table SET category = :newCategory WHERE activity = :activityName")
//    void updateActivityCategory(String activityName, String newCategory);

    @Query("DELETE FROM timeLog_table")
    void deleteAll();

    @Query("SELECT COUNT(id) FROM timeLog_table")
    int metaEntryCount();

    @Query("SELECT MIN(timestamp_created) FROM timeLog_table")
    LiveData<Long> metaOldestEntry();

    @Query("SELECT MAX(timestamp_modified) as newest FROM timeLog_table")
    LiveData<Long> metaNewestEntry();

    @Query("SELECT * FROM timeLog_table ORDER BY timestamp_modified DESC")
    LiveData<List<TimeLog>> getAllTimeLogs();
}
