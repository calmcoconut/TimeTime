package com.example.timetime.database;

import androidx.lifecycle.LiveData;
import com.example.timetime.database.dao.*;
import com.example.timetime.database.entity.Color;

import java.util.List;

public class appRepository {
    // all DAOs
    private ActivityDao mActivityDao;
    private CategoryDao mCategoryDao;
    private ColorDao mColorDao;
    private IconDao mIconDao;
    private TimeTrackerDao mTimeTrackerDao;

    // all cached data declarations
    private LiveData<List<Color>> mAllColors;


}
