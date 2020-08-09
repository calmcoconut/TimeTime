package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.timetime.database.entity.TimeLog;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface TimeLogDao {
    @Query("DELETE FROM timeLog_table")
    void deleteAll();

    // getters
    @Query("SELECT * FROM timeLog_table ORDER BY timestamp_modified DESC")
    LiveData<List<TimeLog>> getAllTimeLogs();

    @Query("SELECT * FROM timeLog_table ORDER BY timestamp_modified DESC LIMIT 1")
    Single<TimeLog> getMostRecentSingleTimeLogEntry();

    @Query("SELECT * FROM timeLog_table WHERE id =:timeLogId LIMIT 1")
    TimeLog getTimeLogById(Long timeLogId);

    @Query("SELECT MAX(timestamp_modified) as newest FROM timeLog_table")
    LiveData<Long> getMostRecentTimeStamp();

    // INSERTERS

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeLog timeTracker);

    // UPDATERS

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
    void updateActivityIcon(String activityName, int newIcon);

    @Query("UPDATE timeLog_table set category = :newCategoryName WHERE category = :oldCategoryName")
    void updateCategory(String oldCategoryName, String newCategoryName);

    @Query("UPDATE timeLog_table SET activity_color = :newColor WHERE activity = :activityName")
    void updateActivityColor(String activityName, String newColor);

    @Query("UPDATE timeLog_table SET category = :newCategory WHERE activity = :activityName")
    void updateActivityCategory(String activityName, String newCategory);

    // META

    @Query("SELECT MIN(timestamp_created) FROM timeLog_table")
    LiveData<Long> getOldestTimeStamp();

    @Query("SELECT * FROM TIMELOG_TABLE WHERE (timestamp_created>=:fromDay OR timestamp_modified>=:fromDay) AND " +
            "(timestamp_created<=:toDay OR timestamp_modified<=:toDay)")
    LiveData<List<TimeLog>> getTimeLogsFromDayToDay(Long fromDay, Long toDay);
}
