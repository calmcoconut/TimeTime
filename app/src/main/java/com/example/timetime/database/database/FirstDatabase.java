package com.example.timetime.database.database;

import android.util.Log;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.*;
import com.example.timetime.utils.DevProperties;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FirstDatabase {
    private final static Long DAY_SECONDS = 86400L;
    private final static Long HOUR_SECONDS = 3600L;
    private final static Long MINUTE_SECONDS = 60L;
    private final Icon[] mIconArray;
    private final Color[] mColorArray;
    private final Category[] mCategoryArray;
    private final Activity[] mActivityArray;
    private final TimeLog[] mTimeLog;

    public FirstDatabase() {
        this.mIconArray = createDefaultIconArray();
        this.mColorArray = createDefaultColorArray();
        this.mCategoryArray = createDefaultCategoryArray();
        this.mActivityArray = createDefaultActivityArray();
        this.mTimeLog = DevProperties.IS_DEV_DB ? createTestingTimeLog() : createDefaultTimeLog();
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

    private TimeLog[] createDefaultTimeLog() {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long timeStampNow = timeLogic.getCurrentDateTimeForDatabaseStorage();
        Long previousTimeStamp = timeStampNow - (MINUTE_SECONDS + 1L);
        TimeLog[] timeLogs = new TimeLog[]{new TimeLog(previousTimeStamp
                , timeStampNow
                , DevProperties.WELCOME_TIME_LOG
                , "ff5252"
                , this.mIconArray[0].getIcon()
                , DevProperties.WELCOME_TIME_LOG)};
        timeLogs[0].setTimeLogId(DevProperties.WELCOME_TIME_LOG_ID);

        return timeLogs;
    }

    private TimeLog[] createTestingTimeLog() {
        TimeLogic timeLogic = TimeLogic.newInstance();
        TimeLog[] timeLogs = new TimeLog[5];

        Long timeStampAtInstallation = timeLogic.getCurrentDateTimeForDatabaseStorage();

        TimeLog weekBefore = new TimeLog(timeStampAtInstallation - (DAY_SECONDS * 7)
                , timeStampAtInstallation - (DAY_SECONDS * 5)
                , mActivityArray[0].getActivity()
                , mActivityArray[0].getColor()
                , mActivityArray[0].getIcon()
                , mActivityArray[0].getCategory());
        TimeLog twoDaysAgo = new TimeLog(timeStampAtInstallation - (DAY_SECONDS * 2)
                , timeStampAtInstallation - (DAY_SECONDS * 2) + (HOUR_SECONDS * 6) + 1
                , mActivityArray[1].getActivity()
                , mActivityArray[1].getColor()
                , mActivityArray[1].getIcon()
                , mActivityArray[1].getCategory());
        TimeLog oneDayAgo = new TimeLog(timeStampAtInstallation - (DAY_SECONDS * 1)
                , timeStampAtInstallation - (DAY_SECONDS * 1) + (HOUR_SECONDS * 2) + 1
                , mActivityArray[2].getActivity()
                , mActivityArray[2].getColor()
                , mActivityArray[2].getIcon()
                , mActivityArray[2].getCategory());
        TimeLog twoHoursAgo = new TimeLog(timeStampAtInstallation - (HOUR_SECONDS * 2)
                , timeStampAtInstallation - (HOUR_SECONDS * 1)
                , mActivityArray[3].getActivity()
                , mActivityArray[3].getColor()
                , mActivityArray[3].getIcon()
                , mActivityArray[3].getCategory());
        TimeLog halfAnHourAgo = new TimeLog(timeStampAtInstallation - (MINUTE_SECONDS * 30)
                , timeStampAtInstallation - (MINUTE_SECONDS * 10)
                , mActivityArray[4].getActivity()
                , mActivityArray[4].getColor()
                , mActivityArray[4].getIcon()
                , mActivityArray[4].getCategory());
        timeLogs[0] = weekBefore;
        timeLogs[1] = twoDaysAgo;
        timeLogs[2] = oneDayAgo;
        timeLogs[3] = twoHoursAgo;
        timeLogs[4] = halfAnHourAgo;

        return timeLogs;
    }

    public TimeLog[] getTimeLogArray() {
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
