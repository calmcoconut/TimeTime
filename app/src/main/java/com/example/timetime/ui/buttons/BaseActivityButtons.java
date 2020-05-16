package com.example.timetime.ui.buttons;

import android.content.Context;
import android.widget.GridLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class BaseActivityButtons {

    public void setUpActivityButtons(LifecycleOwner lifecycleOwner, ActivityViewModel activityViewModel,
                                     List<Activity> activitiesList, Context gridContext, GridLayout gridLayout,
                                     MaterialButton TEMPLATE_BUTTON, boolean isFragmentActivity) {
        // TODO assure that minimum number of activities in the database is 1 or THIS WILL LOOP FOREVER.
        activityViewModel.getAllActivities().observe(lifecycleOwner, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                if (activitiesList == null) {
                    setUpActivityButtons(lifecycleOwner, activityViewModel,
                            activitiesList, gridContext, gridLayout,
                            TEMPLATE_BUTTON, isFragmentActivity);
                } else {
                    for (Activity activity : activities) {
                        MaterialButton materialButton = new ActivityMaterialButton(activity, TEMPLATE_BUTTON,
                                gridContext, activityViewModel).getActivityMaterialButton();

                        ActivityMaterialButton.ActivityButtonTimeLogOnClicks setUpActivityButtonOnClicks =
                                new ActivityMaterialButton.ActivityButtonTimeLogOnClicks();
                        setUpActivityButtonOnClicks.onClickSubmitTimeLog(materialButton,
                                lifecycleOwner,
                                activityViewModel, gridContext);

                        gridLayout.addView(materialButton);
                    }
                }
            }
        });
    }
}
