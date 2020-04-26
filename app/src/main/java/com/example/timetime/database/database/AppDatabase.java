package com.example.timetime.database.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.timetime.database.dao.*;
import com.example.timetime.database.entity.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Activity.class, Category.class, Color.class, Icon.class, TimeLog.class}
        , version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // access our respective DAO objects
    public abstract ColorDao colorDao();

    public abstract IconDao iconDao();

    public abstract CategoryDao categoryDao();

    public abstract ActivityDao activityDao();

    public abstract TimeLogDao timeLogDao();

    // Instance of our database
    private static volatile AppDatabase INSTANCE;
    // Threads for async, background tasks
    private static final int NUMBER_OF_THREADS = 4;
    // executor to handle background, async tasks
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // onOpen database call back for testing
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        // override onOpen for testing purposes
        // TODO remove testing before release
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                UtilityClass.deleteAllDatabaseRows();
                UtilityClass.loadDefaultDatabase();
            });
        }
    };

    // singleton class to initiate the db when called
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class UtilityClass {

        public static void deleteAllDatabaseRows() {
            ColorDao colorDao = INSTANCE.colorDao();
            colorDao.deleteAll();
            IconDao iconDao = INSTANCE.iconDao();
            iconDao.deleteAll();
            CategoryDao categoryDao = INSTANCE.categoryDao();
            categoryDao.deleteAll();
            ActivityDao activityDao = INSTANCE.activityDao();
            activityDao.deleteAll();
            TimeLogDao timeLogDao = INSTANCE.timeLogDao();
            timeLogDao.deleteAll();
        }
        public static void loadDefaultDatabase() {
            ColorDao colorDao = INSTANCE.colorDao();
            IconDao iconDao = INSTANCE.iconDao();
            CategoryDao categoryDao = INSTANCE.categoryDao();
            ActivityDao activityDao = INSTANCE.activityDao();
            TimeLogDao timeLogDao = INSTANCE.timeLogDao();
            FirstDatabase firstDatabase = new FirstDatabase();
            for (Color color : firstDatabase.getColorArray()) {
                colorDao.insert(color);
                Log.d("TESTING DATABASE ", "ADDED DATABASE");
            }
            for (Icon icon : firstDatabase.getIconArray()) {
                iconDao.insert(icon);
            }
            for (Category category : firstDatabase.getCategoryArray()) {
                categoryDao.insert(category);
            }
            for (Activity activity : firstDatabase.getActivityArray()) {
                activityDao.insert(activity);
            }
            timeLogDao.insert(firstDatabase.getTimeLog());
        }
    }
}