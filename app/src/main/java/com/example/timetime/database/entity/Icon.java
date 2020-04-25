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
    private int mIcon;

    public Icon (@NonNull int icon) {
        this.mIcon=icon;
    }

    @NonNull
    public int getIcon() {
        return this.mIcon;
    }

    public void setIcon(@NonNull int Icon) {
        this.mIcon = Icon;
    }
}
