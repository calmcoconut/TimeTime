package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.timetime.database.entity.Activity;

import java.util.List;

@Dao
public interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Activity activity);

    @Query("DELETE FROM activity_table")
    void deleteAll();

    @Query("SELECT * FROM activity_table WHERE activity = :activityName LIMIT 1")
    Activity getActivityByName(String activityName);

    @Query("SELECT * FROM activity_table")
    LiveData<List<Activity>> getAllActivity();
}
