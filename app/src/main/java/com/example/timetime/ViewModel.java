package com.example.timetime;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.*;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    // cached data
    private LiveData<List<Activity>> mAllActivities;
    private LiveData<List<Category>> mAllCategories;
    private LiveData<List<Color>> mAllColors;
    private LiveData<List<Icon>> mAllIcons;
    private LiveData<List<TimeLog>> mAllTimeLogs;

    public ViewModel(@NonNull Application application) {
        super(application);
    }
}
