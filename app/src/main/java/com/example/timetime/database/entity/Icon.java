package com.example.timetime.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "icon_table")
public class Icon {

    @PrimaryKey
    @ColumnInfo(name = "icon")
    @NonNull
    private String mIcon;

    public Icon (@NonNull String icon) {
        this.mIcon=icon;
    }

    @NonNull
    public String getIcon() {
        return this.mIcon;
    }

    public void setIcon(@NonNull String Icon) {
        this.mIcon = Icon;
    }
}
