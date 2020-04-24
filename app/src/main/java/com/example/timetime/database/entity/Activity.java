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
    private Category mCategory;

    @ColumnInfo(name = "icon")
    @NonNull
    private Icon mIcon;

    @ColumnInfo(name = "color")
    @NonNull
    private Color mColor;

    public Activity (@NotNull String activity, @NotNull Category category,
                     @NotNull Icon icon, @NotNull Color color) {
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
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(@NonNull Category category) {
        this.mCategory = category;
    }

    @NonNull
    public Icon getIcon() {
        return mIcon;
    }

    public void setIcon(@NonNull Icon icon) {
        this.mIcon = icon;
    }

    @NonNull
    public Color getColor() {
        return mColor;
    }

    public void setColor(@NonNull Color color) {
        this.mColor = color;
    }
}
