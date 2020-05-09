package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.BaseActivityButtons;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogTimeToActivity extends AppCompatActivity {
    private MaterialButton TEMPLATE_BUTTON;
    Toolbar toolbar;
    private GridLayout mGridLayout;
    private Context mGridContext;
    private ActivityViewModel mActivityViewModel;
    private List<Activity> mActivities;
    private TimeLogic timeLogic;
    private String mToolBarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        mGridLayout = findViewById(R.id.activity_time_log_gridView);
        mGridContext = mGridLayout.getContext();
        TEMPLATE_BUTTON = findViewById(R.id.activity_time_log_button_1);
        timeLogic = TimeLogic.newInstance();
        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        toolbar = findViewById(R.id.activity_time_log_toolbar);
        mActivities = new ArrayList<Activity>();
        BaseActivityButtons baseActivityButtons = new BaseActivityButtons();

        mActivityViewModel.getLastSinceModified().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long latestModifiedTime) {
                if (latestModifiedTime == null) {
                    mToolBarTime = "not working";
                } else {
                    Log.d("LATEST TIMESTAMP", timeLogic.getHumanFormattedTimeBetweenDbValueAndNow(latestModifiedTime));
                    mToolBarTime = timeLogic.getHumanFormattedTimeBetweenDbValueAndNow(latestModifiedTime);
                    setUpToolBar(false);
                }
            }
        });
        setUpToolBar(true);
        baseActivityButtons.setUpActivityButtons(LogTimeToActivity.this, mActivityViewModel, mActivities,
                mGridContext,
                mGridLayout,
                TEMPLATE_BUTTON, false);
    }

    private void setUpToolBar(boolean initialSetUp) {
        if (initialSetUp) {
            toolbar = findViewById(R.id.activity_time_log_toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (mToolBarTime == null) {
            setTitle(mToolBarTime);
        } else {
            setTitle("Last Log: " + mToolBarTime.toString().toUpperCase());
        }
    }
}