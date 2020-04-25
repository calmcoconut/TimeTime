package com.example.timetime.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timeLog_table")
public class TimeLog {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    @NonNull
    private Long mId;

    @ColumnInfo(name = "timestamp_created")
    @NonNull
    private Long mTimestamp_created;

    @ColumnInfo(name = "timestamp_modified")
    @NonNull
    private Long mTimestamp_modified;

    @ColumnInfo(name = "activity")
    private String mActivity;

    @ColumnInfo(name = "category")
    private String mCategory;


    public TimeLog(@NonNull Long id, @NonNull Long timestamp_created
            , @NonNull Long timestamp_modified
            , String activity, String category) {
        this.mId = id;
        this.mTimestamp_created = timestamp_created;
        this.mTimestamp_modified = timestamp_modified;
        this.mActivity = activity;
        this.mCategory = category;
    }

    @NonNull
    public Long getId() {
        return mId;
    }

    public void setId(@NonNull Long Id) {
        this.mId = Id;
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
}