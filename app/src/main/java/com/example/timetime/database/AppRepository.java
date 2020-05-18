package com.example.timetime.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.dao.*;
import com.example.timetime.database.database.AppDatabase;
import com.example.timetime.database.entity.*;

import java.util.List;

public class AppRepository {
    // all DAOs
    private ActivityDao mActivityDao;
    private CategoryDao mCategoryDao;
    private ColorDao mColorDao;
    private IconDao mIconDao;
    private TimeLogDao mTimeLogDao;

    // all cached data declarations
    private LiveData<List<Activity>> mAllActivities;
    private LiveData<List<Category>> mAllCategories;
    private LiveData<List<Color>> mAllColors;
    private LiveData<List<Icon>> mAllIcons;
    private LiveData<List<TimeLog>> mAllTimeTracker;
    private LiveData<Long> mMostRecentModified;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        //DAOs
        mActivityDao = db.activityDao();
        mCategoryDao = db.categoryDao();
        mColorDao = db.colorDao();
        mIconDao = db.iconDao();
        mTimeLogDao = db.timeLogDao();
        // DATA
        mAllActivities = mActivityDao.getAllActivity();
        mAllCategories = mCategoryDao.getAllCategories();
        mAllColors = mColorDao.getAllColors();
        mAllIcons = mIconDao.getAllIcons();
        mAllTimeTracker = mTimeLogDao.getAllTimeLogs();
        mMostRecentModified = mTimeLogDao.metaNewestEntry();
    }

    // getters
    public LiveData<List<Activity>> getAllActivities() {
        return mAllActivities;
    }

    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    public LiveData<List<Color>> getAllColors() {
        return mAllColors;
    }

    public LiveData<List<Icon>> getAllIcons() {
        return mAllIcons;
    }

    public LiveData<List<TimeLog>> getAllTimeLogs() {
        return mAllTimeTracker;
    }

    public LiveData<Long> getMostRecentModified() {
        return mMostRecentModified;
    }

    public LiveData<Activity> findActivityByName(String activityName) {
        return mActivityDao.findActivityByName(activityName);
    }

    // insert into database methods
    public void insertActivity(final Activity activity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mActivityDao.insert(activity);
            }
        });
    }

    public void insertCategory(final Category category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDao.insert(category);
            }
        });
    }

    public void insertColor(final Color color) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mColorDao.insert(color);
            }
        });
    }

    public void insertIcon(final Icon icon) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mIconDao.insert(icon);
            }
        });
    }

    public void insertTimeLog(final TimeLog timeLog) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTimeLogDao.insert(timeLog);
            }
        });
    }

    // update methods for database

    public void updateActivity(Activity oldActivity, Activity newActivity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String oldName = oldActivity.getActivity();
                String newName = newActivity.getActivity();
                String newCategory = newActivity.getCategory();
                int newIcon = newActivity.getIcon();
                String newColor = newActivity.getColor();

                mActivityDao.updateActivity(oldName,newName,newCategory,newIcon,newColor);
            }
        });
    }
}
