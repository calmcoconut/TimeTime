package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.timetime.database.entity.TimeTracker;

import java.util.List;

public interface TimeTrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (TimeTracker timeTracker);

    @Update
     void update (TimeTracker timeTracker);

    @Query("DELETE FROM timeTracker_table")
    void deleteAll();

    @Query("SELECT COUNT(id) FROM timeTracker_table")
    int metaEntryCount();

    @Query("SELECT MIN(timestamp_created) FROM timeTracker_table")
    Long metaOldestEntry();

    @Query("SELECT MAX(timestamp_created) FROM timeTracker_table")
    Long metaNewestEntry();

    @Query("SELECT * FROM timeTracker_table")
    LiveData<List<TimeTracker>> getAllTimeTracker();
}
