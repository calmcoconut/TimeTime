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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Activity activity);

    @Query("DELETE FROM activity_table")
    void deleteAll();

    @Query("SELECT * FROM activity_table")
    LiveData<List<Activity>> getAllActivity();

    @Query("SELECT *  FROM activity_table WHERE activity = :activityName")
    LiveData<Activity> findActivityByName(String activityName);

    // updaters
    @Query("UPDATE activity_table SET activity = :newActivityName, category =:newCategoryName, icon = :newIcon, " +
            "color = :newColor" +
            " WHERE " +
            "activity = " +
            ":oldActivityName")
    void updateActivity(String oldActivityName, String newActivityName, String newCategoryName, int newIcon,
                        String newColor);

    @Query("UPDATE activity_table SET category=:newName WHERE category=:oldName")
    void updateCategory(String oldName, String newName);
}
