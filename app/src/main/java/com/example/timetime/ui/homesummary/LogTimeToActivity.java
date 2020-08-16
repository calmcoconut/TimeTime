package com.example.timetime.ui.homesummary;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.buttons.LogTimeToActivityButton;
import com.example.timetime.utils.ParcelableActivity;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.example.timetime.viewmodels.TimeLogViewModel;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LogTimeToActivity extends AppCompatActivity implements LogTimeToActivityButton.OnLongPressActivityButtonListener {
    public static final String ACTIVITY_LIST_KEY = "activities_list";
    public static final String IS_UPDATE_KEY = "is_an_update";
    public static final String FROM_TIME_KEY = "from_time";
    public static final String TO_TIME_KEY = "to_time";
    private MaterialButton TEMPLATE_BUTTON;
    Toolbar toolbar;
    private GridLayout mGridLayout;
    private Context mGridContext;
    private ActivityViewModel mActivityViewModel;
    private TimeLogViewModel timeLogViewModel;
    private TimeLogic timeLogic;
    private MaterialButton leftButton;
    private MaterialButton rightButton;
    private String mToolBarTime;
    LogTimeToActivityButton baseActivityButtons;
    private Long fromTime;
    private Long toTime;
    private boolean isTap = true;
    private boolean isUpdate = false;
    List<Activity> multipleSelectedActivesList;

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
        leftButton = findViewById(R.id.activity_time_log_button_left);
        rightButton = findViewById(R.id.activity_time_log_button_right);

        setLeftButtonOnClick();
        setRightButtonOnClick();

        baseActivityButtons = new LogTimeToActivityButton(this);

        getTimeToDisplayOnToolBar();
        setUpToolBar(true);
        baseActivityButtons.setUpActivityButtons(LogTimeToActivity.this,
                mActivityViewModel,
                timeLogViewModel,
                mGridContext,
                mGridLayout,
                TEMPLATE_BUTTON);
    }

    public void getTimeToDisplayOnToolBar() {
        timeLogViewModel.getMostRecentTimeLogTimeStamp().observe(this, latestModifiedTime -> {
            if (latestModifiedTime == null) {
                mToolBarTime = "error";
            }
            else {
                mToolBarTime = timeLogic.getHumanFormattedTimeBetweenDbValueAndNow(latestModifiedTime);
                setUpToolBar(false);
            }
        });
    }

    public void setUpToolBar(boolean initialSetUp) {
        if (initialSetUp) {
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

    public void setRightButtonOnClick() {
        rightButton.setOnClickListener(v -> {
            if (isTap) {
                finish();
            }
            else {
                launchSubmitMultipleTimeLogsActivity();
            }
        });
    }

    public void launchSubmitMultipleTimeLogsActivity() {
        Bundle bundle = getBundleForMultipleSubmission();
        if (bundle == null) {
            Toast.makeText(LogTimeToActivity.this, "Wait a minute!",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, SubmitMultipleTimeLogs.class);
            intent.putExtra("BUNDLE", bundle);
            startActivity(intent);
        }
    }

    @NotNull
    private Bundle getBundleForMultipleSubmission() {
        List<ParcelableActivity> serializableActivities = this.multipleSelectedActivesList
                .stream().map(ParcelableActivity::new).collect(Collectors.toList());

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ACTIVITY_LIST_KEY, (ArrayList<? extends Parcelable>) serializableActivities);
        bundle.putBoolean(IS_UPDATE_KEY, this.isUpdate);
        toTime = timeLogic.getCurrentDateTimeForDatabaseStorage();
        if (checkMultipleSelectionHasValidTimeFrame()) {
            bundle.putLong(TO_TIME_KEY, timeLogic.getCurrentDateTimeForDatabaseStorage());
            bundle.putLong(FROM_TIME_KEY, fromTime);
            return bundle;
        }
        else {
            return null;
        }
    }

    private boolean checkMultipleSelectionHasValidTimeFrame() {
        final Long[] createdTimeStamp = new Long[1];
        getTimeLogViewModel().getMostRecentTimeLogTimeStamp().observe(LogTimeToActivity.this, latestModifiedTime -> {
                    if (latestModifiedTime != null) {
                        createdTimeStamp[0] = latestModifiedTime;
                    }
                }
        );
        if (toTime.equals(createdTimeStamp[0]) || toTime.equals(createdTimeStamp[0] - TimeUnit.MINUTES.toMillis(1L))) {
            return false;
        }
        else {
            fromTime = createdTimeStamp[0];
            return true;
        }
    }

    public void setLeftButtonOnClick() {
        leftButton.setOnClickListener(v -> {
            // toggles button behavior between default and multiple selection
            if (isTap) {
                defaultButtonBehavior();
            }
            else {
                multipleSelectionButtonBehavior();
            }
        });
    }

    private void multipleSelectionButtonBehavior() {
        leftButton.setText(R.string.time_log_button_left_action);
        rightButton.setText(R.string.time_log_button_right_action);
        multipleSelectionResetButtonColors(baseActivityButtons.getButtonList());
        isTap = true;
    }

    private void defaultButtonBehavior() {
        leftButton.setText("cancel");
        rightButton.setText("submit");
        onLongPressOfActivity(-1, baseActivityButtons.getButtonList());
    }

    @Override
    public void onLongPressOfActivity(int viewId, List<MaterialButton> buttonList) {
        isTap = false;
        hapticFeedBackOnLongClick();
        initMultipleSelectionBehavior(viewId, buttonList);
    }

    private void initMultipleSelectionBehavior(int viewId, List<MaterialButton> buttonList) {
        multipleSelectedActivesList = new ArrayList<>();
        for (MaterialButton button : buttonList) {
            if (button.getId() == viewId) {
                multipleSelectedActivesList.add((Activity) button.getTag());
            }
            else {
                button.setBackgroundColor(Color.GRAY);
            }
        }
    }

    @Override
    public void onShortPressOfActivity(View view) {
        if (isTap) {
            baseActivityButtons.submitTimeLogForActivity(view);
        }
        else {
            Activity a = (Activity) view.getTag();
            if (multipleSelectedActivesList.contains(a)) {
                multipleSelectionRemoveActivity(view, a);
            }
            else {
                multipleSelectionAddActivity(view, a);
            }
        }
    }

    private void multipleSelectionAddActivity(View view, Activity a) {
        multipleSelectedActivesList.add(a);
        view.setBackgroundColor(Color.parseColor("#" + a.getColor()));
    }

    private void multipleSelectionRemoveActivity(View view, Activity a) {
        multipleSelectedActivesList.remove(a);
        view.setBackgroundColor(Color.GRAY);
    }

    private void multipleSelectionResetButtonColors(List<MaterialButton> buttonList) {
        for (MaterialButton button : buttonList) {
            Activity a = (Activity) button.getTag();
            button.setBackgroundColor(Color.parseColor("#" + a.getColor()));
        }
    }

    private void hapticFeedBackOnLongClick() {
        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(VibrationEffect.createOneShot(VibrationEffect.EFFECT_HEAVY_CLICK, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }


    // Getters
    public TimeLogic getTimeLogic() {
        return this.timeLogic;
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    public String getToolBarTime() {
        return this.mToolBarTime;
    }

    public TimeLogViewModel getTimeLogViewModel() {
        return this.timeLogViewModel;
    }

    public void setToolBarTime(String toolBarTime) {
        this.mToolBarTime = toolBarTime;
    }

    // Setters
    public void setUpdate(boolean update) {
        isUpdate = update;
    }

}