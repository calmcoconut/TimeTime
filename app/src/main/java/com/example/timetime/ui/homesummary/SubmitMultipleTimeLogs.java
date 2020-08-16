package com.example.timetime.ui.homesummary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.timetime.R;
import com.example.timetime.utils.ParcelableActivity;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SubmitMultipleTimeLogs extends AppCompatActivity {
    private MaterialButton leftButton;
    private MaterialButton rightButton;
    private boolean isUpdate;
    private List<ParcelableActivity> parcelableActivityList;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        leftButton = findViewById(R.id.activity_time_log_button_left);
        rightButton = findViewById(R.id.activity_time_log_button_right);

        getIntentBundle();
        setRightButtonOnClick();
        setLeftButtonOnClick();

        initSliders();
    }

    private void initSliders() {

    }

    private void getIntentBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        this.parcelableActivityList = bundle != null ? bundle.getParcelableArrayList(LogTimeToActivity.ACTIVITY_LIST_KEY) : null;
        if (bundle != null) {
            this.isUpdate = bundle.getBoolean(LogTimeToActivity.IS_UPDATE_KEY);
        }
    }

    private void setRightButtonOnClick() {
        rightButton.setOnClickListener(v -> {
            Toast.makeText(this, "first activity is " + parcelableActivityList.get(0).getActivityName(),
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void setLeftButtonOnClick() {
        leftButton.setOnClickListener(v -> {
            Toast.makeText(this, "first activity is " + parcelableActivityList.get(0).getActivityName(),
                    Toast.LENGTH_SHORT).show();
        });
    }
}
