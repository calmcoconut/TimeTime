package com.example.timetime.ui.buttons;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.example.timetime.MainActivity;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

public class LogTimeToActivityButton extends BaseActivityButton {

    @Override
    public void setMaterialButtonOnClick(MaterialButton materialButton) {
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTimeLogForActivity(v);
            }
        });
    }

    @Override
    public void setMaterialButtonOnLongClick(MaterialButton materialButton) {
    }

    private void submitTimeLogForActivity(View v) {
        Activity activity = (Activity) v.getTag();
        TimeLog timeLog = createNewTimeLog(activity);
        checkAndSubmitTimeLog(timeLog);
        launchHomeView();
    }

    private void launchHomeView() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }

    private void checkAndSubmitTimeLog(TimeLog timeLog) {
        if (timeLog.getTimestamp_created().equals(timeLog.getTimestamp_modified())) {
            Toast.makeText(getContext(), "Wait a minute!", Toast.LENGTH_SHORT).show();
        }
        else {
            insertTimeLogIntoDataBase(getActivityViewModel(), timeLog);
        }
    }

    private void insertTimeLogIntoDataBase(ActivityViewModel activityViewModel, TimeLog timeLog) {
        activityViewModel.insertTimeLog(timeLog);
    }

    private TimeLog createNewTimeLog(Activity activity) {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long modifiedTimeStamp = timeLogic.getDateTimeForDatabaseStorage();
        final Long[] createdTimeStamp = new Long[1];
        getActivityViewModel().getLastSinceModified().observe(getOwner(), new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long latestModifiedTime) {
                if (latestModifiedTime == null) {
                }
                else {
                    createdTimeStamp[0] = latestModifiedTime;
                }
            }
        });
        return new TimeLog(createdTimeStamp[0], modifiedTimeStamp, activity.getActivity(), activity.getColor(),
                activity.getIcon(), activity.getCategory());
    }
}
