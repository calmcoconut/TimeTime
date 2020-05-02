package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
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
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Objects;

public class TimeLogActivity extends AppCompatActivity {
    Toolbar toolbar;
    private GridLayout mGridLayout;
    private Context mGridContext;
    private ActivityViewModel mActivityViewModel;
    private List<Activity> mActivities;
    private LiveData<Long> mLatestModifiedActivity;
    private TimeLogic timeLogic;
    private String mToolBarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        mGridLayout = findViewById(R.id.activity_time_log_gridView);
        mGridContext = mGridLayout.getContext();

        timeLogic = TimeLogic.newInstance();
        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
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
        setUpActivityButtons();
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
        // TODO assure that minimum number of activities in the database is 1 or THIS WILL LOOP FOREVER.
        mActivityViewModel.getAllActivities().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                mActivities = activities;
                if (mActivities == null) {
                    setUpActivityButtons();
                }
                else {
                    int id = 1;
                    for (Activity activity : activities) {
                        Log.d("current activity button", activity.getActivity());
                        MaterialButton materialButton = setUpMaterialActivityButton(id, activity);
                          mGridLayout.addView(materialButton);

                        id++;
                    }
                }
            }

            private MaterialButton setUpMaterialActivityButton (int id, Activity activity) {
                MaterialButton dummyButton = findViewById(R.id.activity_time_log_button_1);
                ViewGroup.LayoutParams params = dummyButton.getLayoutParams();
//                MaterialButton materialButton =
//                        (MaterialButton) getLayoutInflater().inflate(R.layout.material_activity_button,null);
//
//                materialButton.setLayoutParams(MaterialButton.layo);
                MaterialButton materialButton = new MaterialButton(new ContextThemeWrapper(mGridContext,
                        R.style.activity_log_button),
                        null,
                        R.style.activity_log_button);
                materialButton.setId(View.generateViewId());
                materialButton.setText(activity.getActivity());
                materialButton.setLayoutParams(params);
                materialButton.setVisibility(View.VISIBLE);
                materialButton.setBackgroundColor(Color.parseColor(("#"+activity.getColor())));
                Drawable icon = getDrawable(R.drawable.icon_camera);
                materialButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, icon,null,
                        null);
                icon.setTint(Color.WHITE);

                materialButton.setTag(activity);
                return materialButton;
            }
        });
    }

}
