package com.example.timetime.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.dao.*;
import com.example.timetime.database.database.AppDatabase;
import com.example.timetime.database.entity.*;
import io.reactivex.Single;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        mMostRecentModified = mTimeLogDao.getMostRecentTimeStamp();
    }

    // TIME LOG
    public void insertTimeLog(final TimeLog timeLog) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
                    Single<TimeLog> timeLogSingle = getMostRecentTimeLogSingle();
                    final TimeLog[] oldTimeLog = new TimeLog[1];
                    timeLogSingle.subscribe(t -> {
                        oldTimeLog[0] = t;
                    });
                    while (oldTimeLog[0] == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (oldTimeLog[0].getActivity().equals(timeLog.getActivity())) {
                        timeLog.setTimestamp_created(oldTimeLog[0].getTimestamp_created());
                        updateTimeLogById(oldTimeLog[0], timeLog);
                    }
                    else {
                        mTimeLogDao.insert(timeLog);
                    }
                }
        );
    }

    public LiveData<List<TimeLog>> getAllTimeLogs() {
        return mAllTimeTracker;
    }

    public Single<TimeLog> getMostRecentTimeLogSingle() {
        return mTimeLogDao.getMostRecentSingleTimeLogEntry();
    }

    public LiveData<Long> getMostRecentTimeLogTimeStamp() {
        return mMostRecentModified;
    }

    public LiveData<List<TimeLog>> getTimeLogsFromDayToDay(Long fromDate, Long toDate) {
        return mTimeLogDao.getTimeLogsFromDayToDay(fromDate, toDate);
    }

    public void updateTimeLogById(TimeLog oldTimeLog, TimeLog newTimeLog) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                {
                    mTimeLogDao.updateTimeLogUsingID(oldTimeLog.getTimeLogId()
                            , newTimeLog.getTimestamp_created()
                            , newTimeLog.getTimestamp_modified()
                            , newTimeLog.getActivity()
                            , newTimeLog.getCategory()
                            , newTimeLog.getActivityIcon()
                            , newTimeLog.getActivityColor());
                }
        );
    }

    // ACTIVITY
    public LiveData<List<Activity>> getAllActivities() {
        return mAllActivities;
    }

    public LiveData<Activity> getActivityByName(String activityName) {
        return mActivityDao.getActivityByName(activityName);
    }

    public void insertActivity(final Activity activity) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mActivityDao.insert(activity));
    }

    public void updateActivity(Activity oldActivity, Activity newActivity) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            String oldName = oldActivity.getActivity();
            String newName = newActivity.getActivity();
            String newCategory = newActivity.getCategory();
            int newIcon = newActivity.getIcon();
            String newColor = newActivity.getColor();

            mActivityDao.updateActivity(oldName, newName, newCategory, newIcon, newColor);
            mTimeLogDao.updateActivity(oldName, newName, newCategory, newIcon, newColor);
        });
    }

    // CATEGORY
    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    public Category getCategoryByName(String category) throws InterruptedException, ExecutionException {
        final Category[] c = new Category[1];
        Future future = AppDatabase.databaseWriteExecutor.submit(() ->
                c[0] = mCategoryDao.getCategoryByName(category));
        while (future.get() != null) {
            Thread.sleep(500);
        }

        return c[0];
    }

    public void updateCategory(Category oldCategory, Category newCategory) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            String oldName = oldCategory.getCategory();
            String newName = newCategory.getCategory();
            String newColor = newCategory.getColor();

            mCategoryDao.updateCategory(oldName, newName, newColor);
            mActivityDao.updateCategory(oldName, newName);
            mTimeLogDao.updateCategory(oldName, newName);
        });
    }

    public void insertCategory(final Category category) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mCategoryDao.insert(category));
    }

    public void deleteCategory(String category) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mCategoryDao.deleteCategory(category));
    }


    // OTHER
    public LiveData<List<Color>> getAllColors() {
        return mAllColors;
    }

    public LiveData<List<Icon>> getAllIcons() {
        return mAllIcons;
    }


    public void insertColor(final Color color) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mColorDao.insert(color));
    }

    public void insertIcon(final Icon icon) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mIconDao.insert(icon));
    }

}
