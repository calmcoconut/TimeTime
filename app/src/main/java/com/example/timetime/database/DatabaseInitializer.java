package com.example.timetime.database;

import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.timetime.database.database.AppDatabase;
import com.example.timetime.database.entity.Colors;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    public static Colors addColor (final AppDatabase db, Colors color) {
        db.colorsDao().insertAll(color);
        return color;
    }

    private static void populateWithTestData(AppDatabase db){
        Colors color = new Colors();
        color.setColor("FF4081");
        addColor(db,color);

        List<Colors> colorsList = db.colorsDao().getAll();
        Log.d(DatabaseInitializer.TAG,"Rows Count: " + colorsList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final AppDatabase mDb;

        PopulateDbAsync (AppDatabase db){
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            populateWithTestData(mDb);
            return null;
        }
    }
}
