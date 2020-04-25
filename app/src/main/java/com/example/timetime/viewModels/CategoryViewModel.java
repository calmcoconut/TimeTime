package com.example.timetime.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.Category;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;

    // cached data
    private LiveData<List<Category>> mAllCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllCategories = mAppRepository.getAllCategories();
    }
    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    public void insert(Category category) {
        mAppRepository.insertCategory(category);
    }
}
