package com.example.timetime.ui.homesummary;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.buttons.LogTimeToActivityButton;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.example.timetime.viewmodels.TimeLogViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogTimeToActivity extends AppCompatActivity implements LogTimeToActivityButton.OnLongPressActivityButtonListener {
    private MaterialButton TEMPLATE_BUTTON;
    Toolbar toolbar;
    private GridLayout mGridLayout;
    private Context mGridContext;
    private ActivityViewModel mActivityViewModel;
    private TimeLogViewModel timeLogViewModel;
    private TimeLogic timeLogic;
    private String mToolBarTime;
    LogTimeToActivityButton baseActivityButtons;
    List<Activity> multipleSelectedActivesList;
    private boolean isTap = true;

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

        baseActivityButtons = new LogTimeToActivityButton(this);

        getTimeToDisplayOnToolBar();
        setUpToolBar(true);
        baseActivityButtons.setUpActivityButtons(LogTimeToActivity.this,
                mActivityViewModel,
                timeLogViewModel,
                mGridContext,
                mGridLayout,
                TEMPLATE_BUTTON);

        initActionButtons();
    }

    private void initActionButtons() {
//        LinearLayout ll = findViewById(R.id.activity_time_log_linear_root);
//        MaterialButton leftButton = new MaterialButton(this);
//        MaterialButton rightButton = new MaterialButton(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        leftButton.setLayoutParams(params);
//        leftButton.setText("test");
//        leftButton.setLayoutParams(params);
//        leftButton.setId(555);
//        leftButton.setGravity(Gravity.BOTTOM);
//        ll.addView(leftButton);
    }

    public void getTimeToDisplayOnToolBar() {
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

    public void setUpToolBar(boolean initialSetUp) {
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

    @Override
    public void onLongPressOfActivity(int viewId, List<MaterialButton> buttonList) {
        isTap = false;
        hapticFeedBackOnLongClick();
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
                multipleSelectedActivesList.remove(a);
                view.setBackgroundColor(Color.GRAY);
            }
            else {
                multipleSelectedActivesList.add(a);
                view.setBackgroundColor(Color.parseColor("#" + a.getColor()));
            }
        }
    }

    private void hapticFeedBackOnLongClick() {
        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(VibrationEffect.createOneShot(VibrationEffect.EFFECT_HEAVY_CLICK, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }


    // getters
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
}