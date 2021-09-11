package com.example.timetime.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity(tableName = "category_table")
public class Category {
    @PrimaryKey
    @ColumnInfo(name="category")
    @NonNull
    private String mCategory;
    @ColumnInfo(name = "color")
    @NonNull
    private String mColor;

    public Category (@NotNull String category, @NotNull String color) {
        this.mCategory = category;
        this.mColor=color;
    }

    @NonNull
    public String getCategory() {
        return mCategory;
    }

    public void setCategory(@NonNull String Category) {
        this.mCategory = Category;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String Color) {
        this.mColor = Color;
    }
}
