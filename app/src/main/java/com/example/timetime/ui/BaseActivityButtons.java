package com.example.timetime.ui;

import android.content.Context;
import android.util.Log;
import android.widget.GridLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.homesummary.ActivityMaterialButton;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Objects;

public class BaseActivityButtons {

    private String mToolBarTime;
    private final TimeLogic timeLogic = TimeLogic.newInstance();

    public void setUpActivityButtons(LifecycleOwner lifecycleOwner, ActivityViewModel activityViewModel,
                                     List<Activity> activitiesList, Context gridContext, GridLayout gridLayout,
                                     MaterialButton TEMPLATE_BUTTON) {
        // TODO assure that minimum number of activities in the database is 1 or THIS WILL LOOP FOREVER.
        activityViewModel.getAllActivities().observe(lifecycleOwner, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                if (activitiesList == null) {
                    setUpActivityButtons(lifecycleOwner, activityViewModel,
                            activitiesList, gridContext, gridLayout,
                            TEMPLATE_BUTTON);
                } else {
                    for (Activity activity : activities) {
                        Log.d("current activity button", activity.getActivity() + activity.getIcon());
                        MaterialButton materialButton = new ActivityMaterialButton(activity, TEMPLATE_BUTTON,
                                gridContext, activityViewModel, "FUUUUUCK").getActivityMaterialButton();

                        ActivityMaterialButton.SetUpActivityButtonOnClicks setUpActivityButtonOnClicks =
                                new ActivityMaterialButton.SetUpActivityButtonOnClicks();
                        setUpActivityButtonOnClicks.activityButtonOnClickSubmitTimeLog(materialButton,
                                lifecycleOwner,
                                activityViewModel, gridContext);

                        gridLayout.addView(materialButton);
                    }
                }
            }
        });
    }

    public void setUpToolBar(LifecycleOwner lifecycleOwner, ActivityViewModel activityViewModel,
                             AppCompatActivity appCompatActivity, android.app.Activity activity, Toolbar toolbar) {

        setUpLastModifiedObserver(lifecycleOwner, activityViewModel);

        appCompatActivity.setSupportActionBar(toolbar);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (mToolBarTime == null) {
            activity.setTitle(mToolBarTime);
        } else {
            activity.setTitle("Last Log: " + mToolBarTime.toString().toUpperCase());
        }
    }

    private void setUpLastModifiedObserver(LifecycleOwner lifecycleOwner, ActivityViewModel activityViewModel) {
        activityViewModel.getLastSinceModified().observe(lifecycleOwner, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long latestModifiedTime) {
                if (latestModifiedTime == null) {
                    mToolBarTime = "not working";
                    setUpLastModifiedObserver(lifecycleOwner,activityViewModel);
                } else {
                    mToolBarTime = timeLogic.getHumanFormattedTimeBetweenDbValueAndNow(latestModifiedTime);
                }
            }
        });
    }
}
