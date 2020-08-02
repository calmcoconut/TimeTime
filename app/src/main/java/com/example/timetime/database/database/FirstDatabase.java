package com.example.timetime.database.database;

import android.util.Log;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FirstDatabase {
    public static int INITIAL_ICON;
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
        INITIAL_ICON = mIconArray[0].getIcon();
        this.mColorArray = createDefaultColorArray();
        this.mCategoryArray = createDefaultCategoryArray();
        this.mActivityArray = createDefaultActivityArray();
        // TODO REMOVE TEST
        this.mTimeLog = createTestingTimeLog(mIconArray);
    }

    // object arrays for populating the database
    private Icon[] createDefaultIconArray() {
        List<Field> iconDrawables = new ArrayList<>();
        Field[] iconHolder = R.drawable.class.getFields();
        for (int i = 0; i < iconHolder.length; i++) {
            Field field = iconHolder[i];
            if (field.getName().startsWith("icon")) {
                iconDrawables.add(field);
            }
        }
        Icon[] iconArrayIntAddresses = new Icon[iconDrawables.size()];

        for (int count = 0; count < iconDrawables.size(); count++) {
            Log.i("Raw Asset: ", iconDrawables.get(count).getName());

            int resourceID;
            try {
                resourceID = iconDrawables.get(count).getInt(null);
                Icon icon = new Icon(resourceID);
                iconArrayIntAddresses[count] = icon;
            } catch (IllegalAccessException e) { // this may cause problems
                e.printStackTrace();
            }
        }
        return iconArrayIntAddresses;
    }

    private Color[] createDefaultColorArray() {
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
                , "CED7DB", "B0BEC5", "9EA7AA"  // grays
        };
        Color[] colorArray = new Color[defaultColors.length];
        for (int count = 0; count < defaultColors.length; count++) {
            colorArray[count] = new Color(defaultColors[count]);
        }
        return colorArray;
    }

    private Category[] createDefaultCategoryArray() {
        final String[] defaultCategoriesStrings = {"Health", "Work", "Other"};
        Category[] categoryObjectArray = new Category[defaultCategoriesStrings.length];

        // health, red
        categoryObjectArray[0] = new Category(defaultCategoriesStrings[0], this.mColorArray[3].getColor());
        // work, orange
        categoryObjectArray[1] = new Category(defaultCategoriesStrings[1], this.mColorArray[42].getColor());
        // other, gray
        categoryObjectArray[2] = new Category(defaultCategoriesStrings[2], this.mColorArray[49].getColor());

        return categoryObjectArray;
    }

    private Activity[] createDefaultActivityArray() {
        final String[] defaultActivitiesStrings = {
                "Sleep", "Food", "Work",
                "Transportation", "Relaxing"
        };
        Activity[] activityObjectsArray = new Activity[defaultActivitiesStrings.length];

        activityObjectsArray[0] = new Activity(         // sleep act
                defaultActivitiesStrings[0]
                , this.mCategoryArray[0].getCategory()  // health cat
                , this.mIconArray[22].getIcon()         // icon_hotel (alphabetical)
                , this.mColorArray[17].getColor());     // blue
        activityObjectsArray[1] = new Activity(         // eat act
                defaultActivitiesStrings[1]
                , this.mCategoryArray[0].getCategory()  // health cat
                , this.mIconArray[15].getIcon()         // icon_fastfood_24px
                , this.mColorArray[29].getColor());     // green
        activityObjectsArray[2] = new Activity(         // work act
                defaultActivitiesStrings[2]
                , this.mCategoryArray[1].getCategory()  // work cat
                , this.mIconArray[2].getIcon()
                , this.mColorArray[42].getColor());     // orange
        activityObjectsArray[3] = new Activity(         // transportation act
                defaultActivitiesStrings[3]
                , this.mCategoryArray[2].getCategory()  // cat other
                , this.mIconArray[9].getIcon()
                , this.mColorArray[49].getColor());     // grey
        activityObjectsArray[4] = new Activity(         // relaxing act
                defaultActivitiesStrings[4]
                , this.mCategoryArray[0].getCategory()  // health cat
                , this.mIconArray[8].getIcon()
                , this.mColorArray[3].getColor());     // red
        return activityObjectsArray;
    }

    private TimeLog createDefaultTimeLog() {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long timeStamp = timeLogic.getCurrentDateTimeForDatabaseStorage();
        return new TimeLog(timeStamp, timeStamp, "myFirstActivity", "ff5252", 1, "klsdf");
    }

    private TimeLog createTestingTimeLog(Icon[] mIconArray) {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long timeStampNow = timeLogic.getCurrentDateTimeForDatabaseStorage();

        Long previousTimeStamp = timeStampNow - 90060L * 3; // one day, one hour, one minute ago
        timeStampNow = timeStampNow - 3600 - HOUR_SECONDS - (HOUR_SECONDS / 2); // make now() - 01:30

        return new TimeLog(previousTimeStamp, timeStampNow, "Welcome to TimeTime"
                , "ff5252"
                , mIconArray[0].getIcon()
                , "Health");
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
