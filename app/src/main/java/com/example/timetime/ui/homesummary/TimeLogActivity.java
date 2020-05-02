package com.example.timetime.ui.homesummary;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.viewmodels.ActivityViewModel;

import java.util.List;
import java.util.Objects;

public class TimeLogActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ActivityViewModel mActivityViewModel;
    private LiveData<List<Activity>> mActivities;
    private LiveData<Long> mLatestModifiedActivity;
    private TimeLogic timeLogic;
    private String mToolBarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);


        timeLogic = TimeLogic.newInstance();
        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        mActivities = mActivityViewModel.getAllActivities();
        mActivityViewModel.getLastSinceModified().observe(this, new Observer<Long>() {
            // TODO FIGURE OUT WHY IS THIS RETURNING NULL???
            @Override
            public void onChanged(@Nullable final Long latestModifiedTime) {
                if (latestModifiedTime == null) {
                    mToolBarTime = "not working";
                } else {
                    Log.d("LATEST TIMESTAMP",timeLogic.formattedTimeBetweenDbValueAndNow(latestModifiedTime));
                    mToolBarTime = timeLogic.formattedTimeBetweenDbValueAndNow(latestModifiedTime);
                    setUpToolBar(false);
                }
            }
            });
        setUpToolBar(true);
    }



    private void setUpToolBar(boolean initialSetUp) {
        if (initialSetUp) {
            TimeLogic timeLogic = TimeLogic.newInstance();
            toolbar = findViewById(R.id.activity_time_log_toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (mToolBarTime == null) {
            setTitle(mToolBarTime);
        }
        else {
            setTitle("Last Log: " + mToolBarTime.toString().toUpperCase());
        }
    }

    private void setUpActivityButtons() {

    }

}
