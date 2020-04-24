package com.example.timetime.database.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.timetime.database.dao.ColorsDao;
import com.example.timetime.database.entity.Color;

@Database (entities = Color.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract ColorsDao colorsDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE==null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "color-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
