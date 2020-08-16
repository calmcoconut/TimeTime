package com.example.timetime.ui.buttons;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.example.timetime.MainActivity;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.viewmodels.TimeLogViewModel;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LogTimeToActivityButton extends BaseActivityButton {
    private final OnLongPressActivityButtonListener listener;

    public LogTimeToActivityButton(OnLongPressActivityButtonListener onLongPressActivityButtonListener) {
        this.listener = onLongPressActivityButtonListener;
    }

    @Override
    public void setMaterialActivityButtonOnClickAction(MaterialButton materialButton) {
        materialButton.setOnClickListener(listener::onShortPressOfActivity);
    }

    @Override
    public void setMaterialButtonOnLongClickAction(MaterialButton materialButton) {
        materialButton.setOnLongClickListener(v -> {
            listener.onLongPressOfActivity(v.getId(), getButtonList());
            return true;
        });
    }

    public void submitTimeLogForActivity(View v) {
        TimeLog timeLog = getTimeLog(v);
        checkAndSubmitTimeLog(timeLog);
        launchHomeView();
    }

    @NotNull
    private TimeLog getTimeLog(View v) {
        Activity activity = (Activity) v.getTag();
        return createNewTimeLog(activity);
    }

    public void launchHomeView() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }

    private void checkAndSubmitTimeLog(TimeLog timeLog) {
        if (timeLog.getTimestamp_created().equals(timeLog.getTimestamp_modified())) {
            Toast.makeText(getContext(), "Wait a minute!", Toast.LENGTH_SHORT).show();
        }
        else {
            insertTimeLogIntoDataBase(getTimeLogViewModel(), timeLog);
        }
    }

    private void insertTimeLogIntoDataBase(TimeLogViewModel timeLogViewModel, TimeLog timeLog) {
        try {
            timeLogViewModel.insertTimeLog(timeLog);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private TimeLog createNewTimeLog(Activity activity) {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long modifiedTimeStamp = timeLogic.getCurrentDateTimeForDatabaseStorage();
        final Long[] createdTimeStamp = new Long[1];
        getTimeLogViewModel().getMostRecentTimeLogTimeStamp().observe(getOwner(), latestModifiedTime -> {
                    if (latestModifiedTime != null) {
                        createdTimeStamp[0] = latestModifiedTime;
                    }
                }
        );
        return new TimeLog(createdTimeStamp[0],
                modifiedTimeStamp,
                activity.getActivity(),
                activity.getColor(),
                activity.getIcon(),
                activity.getCategory());
    }

    public interface OnLongPressActivityButtonListener {
        void onLongPressOfActivity(int viewId, List<MaterialButton> buttonList);

        void onShortPressOfActivity(View view);
    }
}
