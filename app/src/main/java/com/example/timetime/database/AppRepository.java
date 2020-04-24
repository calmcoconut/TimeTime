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
    private TimeTrackerDao mTimeTrackerDao;

    // all cached data declarations
    private LiveData<List<Activity>> mAllActivities;
    private LiveData<List<Category>> mAllCategories;
    private LiveData<List<Color>> mAllColors;
    private LiveData<List<Icon>> mAllIcons;
    private LiveData<List<TimeTracker>> mAllTimeTracker;

    AppRepository (Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        //DAOS
        mActivityDao = db.activityDao();
        mCategoryDao = db.categoryDao();
        mColorDao = db.colorDao();
        mIconDao = db.iconDao();
        mTimeTrackerDao = db.timeTrackerDao();
        // DATA
        mAllActivities = mActivityDao.getAllActivity();
        mAllCategories = mCategoryDao.getAllCategories();
        mAllColors = mColorDao.getAllColors();
        mAllIcons = mIconDao.getAllIcons();
        mAllTimeTracker = mTimeTrackerDao.getAllTimeTracker();
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

    public LiveData<List<TimeTracker>> getAllTimeTracker() {
        return mAllTimeTracker;
    }

    // insert into database methods
    void insertActivity(Activity activity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    void insertCategory(Category category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    void insertColor (Color color) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    void insertIcon (Icon icon) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    void insertTimeTracker (TimeTracker timeTracker) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}
