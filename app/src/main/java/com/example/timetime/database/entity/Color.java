package com.example.timetime.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "color_table")
public class Color {
    // default colors from light red to deep orange
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "color")
    private String mColor;
    public static void defaultColors() {
        final String[] mDefaultColors = {
                "ff5252", "ff1744", "d50000"   // light reds
                , "FF4081", "F50057", "C51162"  // reds
                , "E040FB", "D500F9", "AA00FF"  // pinks
                , "7C4DFF", "651FFF", "6200EA"  // purples
                , "536DFE", "3D5AFE", "304FFE"  // darker blues
                , "448AFF", "2979FF", "2962FF"  // blues
                , "40C4FF", "00B0FF", "0091EA"  // lighter blues
                , "18FFFF", "00E5FF", "00B8D4"  // cloud blues
                , "64FFDA", "1DE9B6", "00BFA5"  // teal blues
                , "69F0AE", "00E676", "00C853"  // blue hue greens
                , "B2FF59", "76FF03", "64DD17"  // greens
                , "EEFF41", "C6FF00", "AEEA00"  // light greens
                , "FFFF00", "FFEA00", "FFD600"  // yellows
                , "FFD740", "FFC400", "FFAB00"  // mellow oranges
                , "FFAB40", "FF9100", "FF6D00"  // light oranges
                , "FF6E40", "FF3D00", "DD2C00"  // deep oranges
        };
    }

    public Color (@NonNull String color) {
        this.mColor = color;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String Color) {
        this.mColor = Color;
    }
}

