package com.example.timetime.ui.homesummary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.MainActivity;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.utils.ParcelableActivity;
import com.example.timetime.viewmodels.TimeLogViewModel;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SubmitMultipleTimeLogs extends AppCompatActivity {
    private TimeLogViewModel timeLogViewModel;
    private TimeLogic timeLogic;
    private GridLayout gridLayout;
    private LinearLayout linearLayout;
    private MaterialButton leftButton;
    private MaterialButton rightButton;
    private AppCompatTextView activesTextMessage;
    private Long fromTime;
    private Long toTime;
    private Integer allocatedTime;
    private boolean isSubmittable;
    private boolean isUpdate;
    private List<ParcelableActivity> parcelableActivityList;
    private List<SeekBar> seekBarList;
    private int[] allProgress;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        timeLogViewModel = new ViewModelProvider(this).get(TimeLogViewModel.class);

        leftButton = findViewById(R.id.activity_time_log_button_left);
        rightButton = findViewById(R.id.activity_time_log_button_right);
        gridLayout = findViewById(R.id.activity_time_log_gridView);
        gridLayout.setVisibility(View.GONE);
        linearLayout = findViewById(R.id.activity_time_log_seek_bar_container);
        linearLayout.setVisibility(View.VISIBLE);
        timeLogic = TimeLogic.newInstance();

        allocatedTime = 0;
        isSubmittable = false;

        extractIntentBundle();
        setRightButtonOnClick();
        setLeftButtonOnClick();

        initText();
        initSliders();
    }

    private void initText() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        activesTextMessage = new AppCompatTextView(this);
        params.setMarginEnd((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()));
        params.setMarginStart((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()));
        activesTextMessage.setLayoutParams(params);
        activesTextMessage.setGravity(Gravity.CENTER);
        activesTextMessage.setTextColor(Color.BLACK);
        setText();
        linearLayout.addView(activesTextMessage);
    }

    private void setText() {
        Long afterAllocatedTime = toTime - allocatedTime;
        String messageTImeString = timeLogic.getHumanFormattedTimeBetweenTwoTimeSpans(fromTime, afterAllocatedTime);
        activesTextMessage.setText(messageTImeString + " left to split between " + parcelableActivityList.size() +
                " activities");
    }

    private void initSliders() {
        int leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                getResources().getDisplayMetrics());
        int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        int rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                getResources().getDisplayMetrics());
        int bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        seekBarList = new ArrayList<>();

        for (ParcelableActivity activity : parcelableActivityList) {
            AppCompatTextView textView = getAppCompatTextViewForSlider(params, activity);
            linearLayout.addView(textView);
            AppCompatSeekBar seekBar = getAppCompatSeekBarForActivity(params);
            seekBarList.add(seekBar);
            allProgress = new int[seekBarList.size()];
            Arrays.fill(allProgress, Math.toIntExact(fromTime));
            setSeekBarListener(activity, textView, seekBar);
            linearLayout.addView(seekBar);
        }
    }

    private void setSeekBarListener(ParcelableActivity activity, AppCompatTextView textView, AppCompatSeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            final int range = Math.toIntExact(toTime) - Math.toIntExact(fromTime);

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int whichIndex = seekBarList.indexOf(seekBar);
                int storedProgress = allProgress[whichIndex];
                int remaining = determineRemainingRoom();
                if (progress > storedProgress) {
                    if (remaining == 0) {
                        seekBar.setProgress(storedProgress);
                        isSubmittable = true;
                    }
                    else {
                        allProgress[whichIndex] = Math.min(storedProgress + remaining, progress);
                        isSubmittable = false;
                    }
                }
                else {
                    allProgress[whichIndex] = progress;
                    isSubmittable = false;
                }

                textView.setText(activity.getActivityName() + "     " + timeLogic.getHumanFormattedTimeBetweenTwoTimeSpans(fromTime, (long) allProgress[whichIndex]));
                setText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            private final Integer determineRemainingRoom() {
                int remainingRoom = 0;
                allocatedTime = 0;
                for (int current : allProgress) {
                    allocatedTime += Math.toIntExact(current - fromTime);
                    remainingRoom = Math.toIntExact(remainingRoom + (current - fromTime));
                }
                return range - remainingRoom;
            }
        });
    }

    @NotNull
    private AppCompatSeekBar getAppCompatSeekBarForActivity(LinearLayout.LayoutParams params) {
        AppCompatSeekBar seekBar = new AppCompatSeekBar(this);
        seekBar.setLayoutParams(params);
        seekBar.setMax(Math.toIntExact(toTime));
        seekBar.setMin(Math.toIntExact(fromTime));
        return seekBar;
    }

    @NotNull
    private AppCompatTextView getAppCompatTextViewForSlider(LinearLayout.LayoutParams params, ParcelableActivity activity) {
        AppCompatTextView textView = new AppCompatTextView(this);
        textView.setLayoutParams(params);
        textView.setText(activity.getActivityName() + "     0min");
        return textView;
    }

    private void extractIntentBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        if (bundle != null) {
            this.isUpdate = bundle.getBoolean(LogTimeToActivity.IS_UPDATE_KEY);
            this.parcelableActivityList = bundle.getParcelableArrayList(LogTimeToActivity.ACTIVITY_LIST_KEY);
            this.fromTime = bundle.getLong(LogTimeToActivity.FROM_TIME_KEY);
            this.toTime = bundle.getLong(LogTimeToActivity.TO_TIME_KEY);
        }
        else {
            finish();
        }
    }

    private void setRightButtonOnClick() {
        rightButton.setText("submit");
        rightButton.setOnClickListener(v -> {
            if (isSubmittable) {
                TimeLog[] timeLogs = createTimeLogs();
                for (TimeLog t : timeLogs) {
                    submitTimeLog(t);
                }
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
            }
            else {
                Toast.makeText(this,
                        "Allocate all available time before submitting",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TimeLog[] createTimeLogs() {
        TimeLog[] timeLogs = new TimeLog[allProgress.length];
        Long currentFromTime = fromTime;
        Long currentToTime;
        for (int i = 0; i < allProgress.length; i++) {
            ParcelableActivity currentActivity = parcelableActivityList.get(parcelableActivityList.size() - i - 1);
            currentToTime = currentFromTime + (allProgress[i] - fromTime);
            timeLogs[i] = new TimeLog(currentFromTime,
                    currentToTime,
                    currentActivity.getActivityName(),
                    currentActivity.getActivityColor(),
                    currentActivity.getActivityIcon(),
                    currentActivity.getCategoryName());
            currentFromTime = currentToTime;
        }
        return timeLogs;
    }

    private void submitTimeLog(TimeLog timeLog) {
        if (isUpdate) {
        }
        else {
            try {
                timeLogViewModel.insertTimeLog(timeLog);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLeftButtonOnClick() {
        leftButton.setText("cancel");
        leftButton.setOnClickListener(v -> {
            finish();
        });
    }
}
