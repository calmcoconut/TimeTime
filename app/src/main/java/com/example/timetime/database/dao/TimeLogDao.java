package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.timetime.database.entity.TimeLog;

import java.util.List;

@Dao
public interface TimeLogDao {
    @Query("DELETE FROM timeLog_table")
    void deleteAll();

    // getters
    @Query("SELECT * FROM timeLog_table ORDER BY timestamp_modified DESC")
    LiveData<List<TimeLog>> getAllTimeLogs();

    @Query("SELECT * FROM timeLog_table ORDER BY timestamp_modified DESC LIMIT 1")
    LiveData<TimeLog> getMostRecentTimeLogEntry();

    @Query("SELECT MAX(timestamp_modified) as newest FROM timeLog_table")
    LiveData<Long> getMostRecentTimeStamp();
    // INSERTERS

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeLog timeTracker);
    // UPDATERS

    @Update
    void update(TimeLog timeTracker);

    @Query("UPDATE timeLog_table " +
            "set timestamp_created = :timeStampCreated, timestamp_modified=:timeStampModified," +
            "activity = :activityName, activity_color= :newColor, activity_icon = :newIcon, category=:categoryName " +
            "WHERE id=:timeLogId")
    void updateTimeLogUsingID(Long timeLogId, Long timeStampCreated, Long timeStampModified, String activityName,
                              String categoryName, int newIcon, String newColor);

    @Query("UPDATE timeLog_table set activity = :newActivityName, activity_color= :newColor, activity_icon = " +
            ":newIcon, category=:newCategoryName WHERE activity=:oldActivityName")
    void updateActivity(String oldActivityName, String newActivityName, String newCategoryName, int newIcon,
                        String newColor);

    @Query("UPDATE timeLog_table SET activity_icon = :newIcon WHERE activity = :activityName")
    void updateActivityColor(String activityName, int newIcon);

    @Query("UPDATE timeLog_table set category = :newCategoryName WHERE category = :oldCategoryName")
    void updateCategory(String oldCategoryName, String newCategoryName);

    @Query("UPDATE timeLog_table SET activity_color = :newColor WHERE activity = :activityName")
    void updateActivityColor(String activityName, String newColor);
    // META

    @Query("UPDATE timeLog_table SET category = :newCategory WHERE activity = :activityName")
    void updateActivityCategory(String activityName, String newCategory);

    @Query("SELECT COUNT(id) FROM timeLog_table")
    int metaEntryCount();

    @Query("SELECT MIN(timestamp_created) FROM timeLog_table")
    LiveData<Long> metaOldestEntry();

    @Query("SELECT * FROM TIMELOG_TABLE WHERE (timestamp_created>=:fromDay OR timestamp_modified>=:fromDay) AND " +
            "(timestamp_created<=:toDay OR timestamp_modified<=:toDay)")
    LiveData<List<TimeLog>> getTimeLogsFromDayToDay(Long fromDay, Long toDay);
}
