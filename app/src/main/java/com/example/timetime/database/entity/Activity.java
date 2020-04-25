package com.example.timetime.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity(tableName = "activity_table")
public class Activity {
    @PrimaryKey
    @ColumnInfo(name="activity")
    @NonNull
    private String mActivity;

    @ColumnInfo(name="category")
    @NonNull
    private String mCategory;

    @ColumnInfo(name = "icon")
    @NonNull
    private String mIcon;

    @ColumnInfo(name = "color")
    @NonNull
    private String mColor;

    public Activity (@NotNull String activity, @NotNull String category,
                     @NotNull String icon, @NotNull String color) {
        this.mActivity = activity;
        this.mCategory = category;
        this.mIcon = icon;
        this.mColor = color;
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

    @NonNull
    public String getIcon() {
        return mIcon;
    }

    public void setIcon(@NonNull String icon) {
        this.mIcon = icon;
    }

    @NonNull
    public String getColor() {
        return mColor;
    }

    public void setColor(@NonNull String color) {
        this.mColor = color;
    }
}
