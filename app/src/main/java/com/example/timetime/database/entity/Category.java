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
    private Color mColor;

    public Category (@NotNull String category, @NotNull Color color) {
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

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color Color) {
        this.mColor = Color;
    }
}
