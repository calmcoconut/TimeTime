package com.example.timetime.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timeLog_table")
public class TimeLog {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private Long timeLogId;

    @ColumnInfo(name = "timestamp_created")
    @NonNull
    private Long mTimestamp_created;

    @ColumnInfo(name = "timestamp_modified")
    @NonNull
    private Long mTimestamp_modified;

    @ColumnInfo(name = "activity")
    @NonNull
    private String mActivity;

    @ColumnInfo(name = "activity_color")
    @NonNull
    private String mActivityColor;

    @ColumnInfo(name = "activity_icon")
    private int mActivityIcon;

    @ColumnInfo(name = "category")
    @NonNull
    private String mCategory;

    @ColumnInfo(name = "number_times_logged")
    private int timesLogged = 1;


    public TimeLog(@NonNull Long timestamp_created
            , @NonNull Long timestamp_modified
            , String activity, String mActivityColor, int mActivityIcon, String category) {
        this.mTimestamp_created = timestamp_created;
        this.mTimestamp_modified = timestamp_modified;
        this.mActivity = activity;
        this.mActivityColor = mActivityColor;
        this.mActivityIcon = mActivityIcon;
        this.mCategory = category;
    }

    @NonNull
    public Long getTimeLogId() {
        return timeLogId;
    }

    public void setTimeLogId(@NonNull Long Id) {
        this.timeLogId = Id;
    }

    @NonNull
    public Long getTimestamp_created() {
        return mTimestamp_created;
    }

    public void setTimestamp_created(@NonNull Long timestamp_created) {
        this.mTimestamp_created = timestamp_created;
    }

    @NonNull
    public Long getTimestamp_modified() {
        return mTimestamp_modified;
    }

    public void setTimestamp_modified(@NonNull Long timestamp_modified) {
        this.mTimestamp_modified = timestamp_modified;
    }

    @NonNull
    public String getActivity() {
        return mActivity;
    }

    public void setActivity(@NonNull String activity) {
        this.mActivity = activity;
    }

    @NonNull
    public String getCategory() {
        return mCategory;
    }

    public void setCategory(@NonNull String category) {
        this.mCategory = category;
    }

    public String getActivityColor() {
        return mActivityColor;
    }

    public void setActivityColor(@NonNull String mActivityColor) {
        this.mActivityColor = mActivityColor;
    }

    public int getActivityIcon() {
        return mActivityIcon;
    }

    public void setActivityIcon(int mActivityIcon) {
        this.mActivityIcon = mActivityIcon;
    }

    public int getTimesLogged() {
        return timesLogged;
    }

    public void setTimesLogged(int timesLogged) {
        this.timesLogged = timesLogged;
    }
}