package com.example.timetime.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Color;
import com.example.timetime.database.entity.Icon;

import java.util.List;

public class ActivityViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    // cached data
    private LiveData<List<Activity>> mAllActivities;
    private LiveData<Long> mLatestTimeStamp;
    private LiveData<List<Color>> mColors;
    private LiveData<List<Icon>> mIcons;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllActivities = mAppRepository.getAllActivities();
        mLatestTimeStamp = mAppRepository.getMostRecentTimeLogTimeStamp();
        mColors = mAppRepository.getAllColors();
        mIcons = mAppRepository.getAllIcons();
    }

    // Getters
    public LiveData<List<Activity>> getAllActivities() {
        return mAllActivities;
    }

    public LiveData<List<Color>> getAllColors() {
        return mColors;
    }

    public LiveData<List<Icon>> getAllIcons() {
        return mIcons;
    }

    // Inserters
    public void insert(Activity activity) {
        mAppRepository.insertActivity(activity);
    }

    // updaters
    public void updateActivity(Activity oldActivity, Activity newActivity) {
        if (!oldActivity.equals(newActivity)) {
            mAppRepository.updateActivity(oldActivity, newActivity);
        }
    }

    // deletion
    public void deleteActivity(Activity activity) {
        mAppRepository.deleteActivity(activity);
    }

}
