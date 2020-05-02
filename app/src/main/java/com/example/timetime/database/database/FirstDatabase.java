package com.example.timetime.database.database;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.*;

import java.lang.reflect.Field;

public class FirstDatabase {

    private final static Long DAY_SECONDS = 86400L;
    private final static Long HOUR_SECONDS = 3600L;
    private final static Long MINUTE_SECONDS = 60L;
    private final Icon[] mIconArray;
    private final Color[] mColorArray;
    private final Category[] mCategoryArray;
    private final Activity[] mActivityArray;
    private final TimeLog mTimeLog;

    public FirstDatabase() {
        this.mIconArray = createDefaultIconArray();
        this.mColorArray = createDefaultColorArray();
        this.mCategoryArray = createDefaultCategoryArray(mColorArray);
        this.mActivityArray = createDefaultActivityArray(mColorArray,mIconArray,mCategoryArray);
        // TODO REMOVE TEST
        this.mTimeLog = createTestingTimeLog() ;
    }

    // object arrays for populating the database
    private Icon[] createDefaultIconArray () {
        Field[] iconDrawables = com.example.timetime.R.raw.class.getFields();
        Icon[] iconArrayIntAddresses = new Icon[iconDrawables.length];

        for (int count=0; count < iconDrawables.length; count++){
            Log.i("Raw Asset: ", iconDrawables[count].getName());
            int resourceID;
            try {
                resourceID = iconDrawables[count].getInt(null);
                Icon icon = new Icon(resourceID);
                Log.d("Raw asset ", String.valueOf(resourceID));
                iconArrayIntAddresses[count] = icon;
            }
            catch (IllegalAccessException e) { // this may cause problems
                e.printStackTrace();
            }
        }
        return iconArrayIntAddresses;
    }
    private Color[] createDefaultColorArray () {
        final String[] defaultColors = {
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
        Color[] colorArray = new Color[defaultColors.length];
        for (int count = 0; count < defaultColors.length; count++) {
            colorArray[count] = new Color(defaultColors[count]);
        }
        return colorArray;
    }
    private Category[] createDefaultCategoryArray (@NonNull Color[] colorArray) {
        final String[] defaultCategories = {"Health","Work","Transportation",
                "Learning","Entertainment",
                "Family","Hobby","Other"
        };
        Category[] categoryArray = new Category[defaultCategories.length];

        int colorIndex = 0;
        for (int count=0; count<defaultCategories.length; count++) {
            categoryArray[count] = new Category(defaultCategories[count], colorArray[colorIndex].getColor());
            colorIndex += 3;
        }
        return categoryArray;
    }
    private Activity[] createDefaultActivityArray(Color[] colorArray, Icon[] iconArray, Category[] categoryArray) {
        final String[] defaultActivities = {
                "Sleep","Working out","Work",
                "Transportation","Studying",
                "Having fun","Family","Hobby"
        };
        Activity[] activityArray = new Activity[defaultActivities.length];

        int colorIdex = 0;
        int iconIndex = 0;
        for (int count=0; count<defaultActivities.length; count++) {
            activityArray[count] = new Activity(defaultActivities[count],categoryArray[count].getCategory()
                    ,iconArray[iconIndex].getIcon(),colorArray[colorIdex].getColor());
            iconIndex += 1;
            colorIdex += 3;
        }
        return activityArray;
    }
    private TimeLog createDefaultTimeLog () {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long timeStamp = timeLogic.getDateTimeForDatabaseStorage();
        return new TimeLog(timeStamp,timeStamp,"myFirstActivity","myFirstCategory");
    }

    private TimeLog createTestingTimeLog () {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long timeStamp = timeLogic.getDateTimeForDatabaseStorage(); // now

        Long previousTimeStamp = timeStamp - 90060L; // one day, one hour, one minute ago
        timeStamp = timeStamp - 3600 - HOUR_SECONDS - (HOUR_SECONDS/2); // make now() - 01:30

        return new TimeLog(previousTimeStamp,timeStamp,"TESTING","other");
    }

    public TimeLog getTimeLog() {
        return mTimeLog;
    }

    public Activity[] getActivityArray() {
        return mActivityArray;
    }

    public Category[] getCategoryArray() {
        return mCategoryArray;
    }

    public Color[] getColorArray() {
        return mColorArray;
    }

    public Icon[] getIconArray() {
        return mIconArray;
    }
}
