package com.example.timetime.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Color;
import com.example.timetime.database.entity.TimeLog;

import java.util.List;

public class ActivityViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    // cached data
    private LiveData<List<Activity>> mAllActivities;
    private LiveData<Long> mLatestTimeStamp;
    private LiveData<List<Color>> mColors;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllActivities = mAppRepository.getAllActivities();
        mLatestTimeStamp = mAppRepository.getMostRecentModified();
        mColors = mAppRepository.getAllColors();
    }
    // Getters
    public LiveData<List<Activity>> getAllActivities() {
        return mAllActivities;
    }
    public LiveData<Long> getLastSinceModified() {
        return mLatestTimeStamp;
    }
    public LiveData<List<Color>> getAllColors() {
        return mColors;
    }

    // Inserters
    public void insert(Activity activity) {
        mAppRepository.insertActivity(activity);
    }
    public void insertTimeLog(TimeLog timeLog) {
        mAppRepository.insertTimeLog(timeLog);
    }

}
