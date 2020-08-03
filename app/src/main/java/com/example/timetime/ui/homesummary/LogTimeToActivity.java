package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.ui.buttons.LogTimeToActivityButton;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.example.timetime.viewmodels.TimeLogViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class LogTimeToActivity extends AppCompatActivity {
    private MaterialButton TEMPLATE_BUTTON;
    Toolbar toolbar;
    private GridLayout mGridLayout;
    private Context mGridContext;
    private ActivityViewModel mActivityViewModel;
    private TimeLogViewModel timeLogViewModel;
    private TimeLogic timeLogic;
    private String mToolBarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        mGridLayout = findViewById(R.id.activity_time_log_gridView);
        mGridContext = mGridLayout.getContext();
        TEMPLATE_BUTTON = findViewById(R.id.activity_button_template);
        timeLogic = TimeLogic.newInstance();
        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        timeLogViewModel = new ViewModelProvider(this).get(TimeLogViewModel.class);
        toolbar = findViewById(R.id.activity_time_log_toolbar);

        LogTimeToActivityButton baseActivityButtons = new LogTimeToActivityButton();

        ObserveChangeInLastModified();
        setUpToolBar(true);
        baseActivityButtons.setUpActivityButtons(LogTimeToActivity.this,
                mActivityViewModel,
                timeLogViewModel,
                mGridContext,
                mGridLayout,
                TEMPLATE_BUTTON);
    }

    private void ObserveChangeInLastModified() {
        timeLogViewModel.getMostRecentTimeLogTimeStamp().observe(this, latestModifiedTime -> {
            if (latestModifiedTime == null) {
                mToolBarTime = "not working";
            }
            else {
                mToolBarTime = timeLogic.getHumanFormattedTimeBetweenDbValueAndNow(latestModifiedTime);
                setUpToolBar(false);
            }
        });
    }

    private void setUpToolBar(boolean initialSetUp) {
        if (initialSetUp) {
            toolbar = findViewById(R.id.activity_time_log_toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (mToolBarTime == null) {
            setTitle(mToolBarTime);
        }
        else {
            setTitle("Last Log: " + mToolBarTime.toUpperCase());
        }
    }
}