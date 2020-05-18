package com.example.timetime.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Category;

import java.util.HashMap;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;

    // cached data
    private LiveData<List<Category>> mAllCategories;
    private LiveData<List<Activity>> mAllActivities;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllCategories = mAppRepository.getAllCategories();
        mAllActivities = mAppRepository.getAllActivities();
    }

    private HashMap<String, List<Activity>> mapCategoryToActivity() {
        HashMap<String, List<Activity>> result = new HashMap<>();
        MediatorLiveData liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(mAllCategories, value -> liveDataMerger.setValue(value));
        liveDataMerger.addSource(mAllActivities, value -> liveDataMerger.setValue(value));

        return result;
    }

    // getters
    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }
    public LiveData<List<Activity>> getAllActivities() {
        return mAllActivities;
    }

    // inserters
    public void insert(Category category) {
        mAppRepository.insertCategory(category);
    }
}
