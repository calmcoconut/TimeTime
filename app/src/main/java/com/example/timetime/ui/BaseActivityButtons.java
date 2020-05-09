package com.example.timetime.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.*;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.homesummary.ActivityMaterialButton;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivityButtons {
    private MaterialButton TEMPLATE_BUTTON;
    Toolbar toolbar;
    private final GridLayout mGridLayout;
    private final Context mGridContext;
    private final ActivityViewModel mActivityViewModel;
    private final TimeLogic timeLogic;
    private final List<MaterialButton> materialActivityButtons;
    private List<Activity> mActivities;
    private LiveData<Long> currentTime;
    private String mToolBarTime;

    public BaseActivityButtons(View view, ViewModelStoreOwner viewModelStoreOwner) {
        materialActivityButtons = new ArrayList<>();
        mGridLayout = view.findViewById(R.id.activity_time_log_gridView);
        mGridContext = mGridLayout.getContext();
        TEMPLATE_BUTTON = view.findViewById(R.id.activity_time_log_button_1);

        timeLogic = TimeLogic.newInstance();
        mActivityViewModel = new ViewModelProvider(viewModelStoreOwner).get(ActivityViewModel.class);
    }

    public void setUpActivityButtons(LifecycleOwner lifecycleOwner) {
        // TODO assure that minimum number of activities in the database is 1 or THIS WILL LOOP FOREVER.
        mActivityViewModel.getAllActivities().observe(lifecycleOwner, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                mActivities = activities;
                if (mActivities == null) {
                    setUpActivityButtons(lifecycleOwner);
                } else {
                    for (Activity activity : activities) {
                        Log.d("current activity button", activity.getActivity() + activity.getIcon());
                        MaterialButton materialButton = new ActivityMaterialButton(activity, TEMPLATE_BUTTON,
                                mGridContext, mActivityViewModel, mToolBarTime).getActivityMaterialButton();

                        ActivityMaterialButton.SetUpActivityButtonOnClicks setUpActivityButtonOnClicks =
                                new ActivityMaterialButton.SetUpActivityButtonOnClicks();
                        setUpActivityButtonOnClicks.activityButtonOnClickSubmitTimeLog(materialButton,
                                lifecycleOwner,
                                mActivityViewModel, mGridContext);

                        mGridLayout.addView(materialButton);
                        materialActivityButtons.add(materialButton);
                    }
                }
            }
        });
    }
}
