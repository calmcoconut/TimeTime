package com.example.timetime.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.Activity;

import java.util.List;

public class ActivityViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    // cached data
    private LiveData<List<Activity>> mAllActivities;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllActivities = mAppRepository.getAllActivities();
    }
    public LiveData<List<Activity>> getAllActivities() {
        return mAllActivities;
    }

    public void insert(Activity activity) {
        mAppRepository.insertActivity(activity);
    }
}
