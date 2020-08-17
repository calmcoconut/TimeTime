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
    private int allocatedTime;
    private Long oldRange;
    private int newMin;
    private int newMax;
    private int newRange;
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

        extractIntentBundle();

        allocatedTime = 0;
        newRange = 100;
        oldRange = toTime - fromTime;
        newMin = 0;
        newMax = 100;
        isSubmittable = false;

        setRightButtonOnClick();
        setLeftButtonOnClick();

        initTimeAvailableText();
        initSliders();
    }

    private void initTimeAvailableText() {
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
        setTimeAvailableText();
        linearLayout.addView(activesTextMessage);
    }

    private void setTimeAvailableText() {
        Long afterAllocatedTime = toTime - getLongFromStandardizedScale(allocatedTime);
        String messageTImeString = timeLogic.getHumanFormattedTimeBetweenTwoTimeSpans(fromTime, afterAllocatedTime);
        activesTextMessage.setText(messageTImeString + " left to split between " + parcelableActivityList.size() +
                " activities");
    }


    private final int standardizeScaleForSliders(long valueToStandardize) {
        // using NewValue = (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin
        return Math.toIntExact(((valueToStandardize - fromTime) * newRange / oldRange));
    }

    private final long getLongFromStandardizedScale(int standardizedValue) {
        return (((standardizedValue * oldRange) / newRange) + fromTime);
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
            AppCompatTextView textView = getPrefabbedTextForSlider(params, activity);
            linearLayout.addView(textView);
            AppCompatSeekBar seekBar = getPrefabbedSeekbar(params);
            seekBarList.add(seekBar);
            allProgress = new int[seekBarList.size()];
            Arrays.fill(allProgress, newMin);
            setSliderListeners(activity, textView, seekBar);
            linearLayout.addView(seekBar);
        }
    }

    @NotNull
    private AppCompatSeekBar getPrefabbedSeekbar(LinearLayout.LayoutParams params) {
        AppCompatSeekBar seekBar = new AppCompatSeekBar(this);
        seekBar.setLayoutParams(params);
        seekBar.setMin(newMin);
        seekBar.setMax(newMax);
        return seekBar;
    }

    private void setSliderListeners(ParcelableActivity activity, AppCompatTextView textView, AppCompatSeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

                textView.setText(activity.getActivityName() + "     " + timeLogic.getHumanFormattedTimeBetweenTwoTimeSpans(fromTime, getLongFromStandardizedScale(allProgress[whichIndex])));
                setTimeAvailableText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            private final int determineRemainingRoom() {
                int remainingRoom = 0;
                allocatedTime = 0;
                for (int current : allProgress) {
                    allocatedTime += current - newMin;
                    remainingRoom = remainingRoom + (current - newMin);
                }
                return newRange - remainingRoom;
            }
        });
    }

    @NotNull
    private AppCompatTextView getPrefabbedTextForSlider(LinearLayout.LayoutParams params, ParcelableActivity activity) {
        AppCompatTextView textView = new AppCompatTextView(this);
        textView.setLayoutParams(params);
        textView.setText(activity.getActivityName() + "     0min");
        textView.setTextColor(Color.parseColor("#" + activity.getActivityColor()));
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

    private void setLeftButtonOnClick() {
        leftButton.setText("cancel");
        leftButton.setOnClickListener(v -> {
            finish();
        });
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
            ParcelableActivity currentActivity = parcelableActivityList.get(i);
            currentToTime = currentFromTime + (getLongFromStandardizedScale(allProgress[i]) - fromTime);
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
}
