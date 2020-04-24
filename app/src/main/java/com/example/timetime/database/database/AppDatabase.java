package com.example.timetime.database.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.timetime.database.dao.ColorDao;
import com.example.timetime.database.dao.IconDao;
import com.example.timetime.database.entity.Color;
import com.example.timetime.database.entity.Icon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Color.class, Icon.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // access our respective DAO objects
    public abstract ColorDao colorDao();
    public abstract IconDao iconDao();

    // Instance of our database
    private static volatile AppDatabase INSTANCE;
    // Threads for async, background tasks
    private static final int NUMBER_OF_THREADS = 4;
    // executor to handle background, async tasks
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
