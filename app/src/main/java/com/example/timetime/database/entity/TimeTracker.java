package com.example.timetime.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timeTracker_table")
public class TimeTracker {
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

    @ColumnInfo(name = "timestamp_human")
    @NonNull
    private String mTimestamp_human;

    @ColumnInfo(name = "activity")
    @NonNull
    private Activity mActivity;

    @ColumnInfo(name = "category")
    @NonNull
    private Category mCategory;


    public TimeTracker(@NonNull Long Id, @NonNull Long Timestamp_created
            ,@NonNull Long Timestamp_modified, @NonNull String Timestamp_human
            ,@NonNull Activity Activity, @NonNull Category Category) {
        this.mId = Id;
        this.mTimestamp_created = Timestamp_created;
        this.mTimestamp_modified = Timestamp_modified;
        this.mTimestamp_human = Timestamp_human;
        this.mActivity = Activity;
        this.mCategory = Category;
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

    public void setTimestamp_created(@NonNull Long Timestamp_created) {
        this.mTimestamp_created = Timestamp_created;
    }

    @NonNull
    public Long getTimestamp_modified() {
        return mTimestamp_modified;
    }

    public void setTimestamp_modified(@NonNull Long Timestamp_modified) {
        this.mTimestamp_modified = Timestamp_modified;
    }

    @NonNull
    public String getTimestamp_human() {
        return mTimestamp_human;
    }

    public void setTimestamp_human(@NonNull String Timestamp_human) {
        this.mTimestamp_human = Timestamp_human;
    }

    @NonNull
    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(@NonNull Activity Activity) {
        this.mActivity = Activity;
    }

    @NonNull
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(@NonNull Category Category) {
        this.mCategory = Category;
    }
}