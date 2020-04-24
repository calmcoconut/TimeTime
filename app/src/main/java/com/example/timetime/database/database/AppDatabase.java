package com.example.timetime.database.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.timetime.database.dao.*;
import com.example.timetime.database.entity.Color;
import com.example.timetime.database.entity.Icon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Color.class, Icon.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // access our respective DAO objects
    public abstract ColorDao colorDao();
    public abstract IconDao iconDao();
    public abstract CategoryDao categoryDao();
    public abstract ActivityDao activityDao();
    public abstract TimeTrackerDao timeTrackerDao();

    // Instance of our database
    private static volatile AppDatabase INSTANCE;
    // Threads for async, background tasks
    private static final int NUMBER_OF_THREADS = 4;
    // executor to handle background, async tasks
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // onOpen database call back
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        // override onOpen for testing purposes
        // TODO remove testing before release
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    };

    // singleton class to initiate the db when called
    static AppDatabase getDatabase (final Context context) {
        if (INSTANCE==null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}