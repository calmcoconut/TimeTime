package com.example.timetime.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Category;

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

    // updaters
    public void updateCategory(Category oldCategory, Category newCategory) {
        if (!oldCategory.equals(newCategory)) {
            mAppRepository.updateCategory(oldCategory, newCategory);
        }
    }
}
