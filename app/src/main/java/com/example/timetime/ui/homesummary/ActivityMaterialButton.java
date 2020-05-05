package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.view.ContextThemeWrapper;
import com.example.timetime.MainActivity;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

public class ActivityMaterialButton {
    private Activity mActivity;
    private MaterialButton mMaterialButton;
    private ActivityViewModel mActivityViewModel;
    private String mToolBarTime;
    private Context mContext;

    public ActivityMaterialButton(Activity mActivity, MaterialButton template_button, Context mContext,
                                  ActivityViewModel activityViewModel, String toolBarTime) {
        this.mActivityViewModel = activityViewModel;
        ViewGroup.LayoutParams params = template_button.getLayoutParams();
        this.mMaterialButton = new MaterialButton(new ContextThemeWrapper(mContext,
                R.style.activity_log_button),
                null,
                R.style.activity_log_button);
        this.mActivity = mActivity;
        this.mMaterialButton.setLayoutParams(params);
        this.mContext = mContext;
        this.mToolBarTime = toolBarTime;
        setUpMaterialActivityButtonBasicAttributes();
        setUpMaterialActivityButtonIcon();
        setUpActivityButtonOnClick();
    }

    private void setUpActivityButtonOnClick() {
        this.mMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityButtonTapSubmitTimeLog(v);
            }
        });
    }

    private void activityButtonTapSubmitTimeLog(View view) {
        Activity activityObject = (Activity) view.getTag();
        if (view.getTag() == null) {
            Toast.makeText(this.mContext, "There has been an error.",
                    Toast.LENGTH_SHORT).show();
        }
        else if (this.mToolBarTime.equals("0MIN")) {
            launchHomeView();
        }
        else {
            TimeLog timeLog = createNewTimeLog(activityObject);
            insertTimeLogIntoDataBase(timeLog);
            launchHomeView();
        }
    }

    private void launchHomeView() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    private void insertTimeLogIntoDataBase(TimeLog timeLog) {
        mActivityViewModel.insertTimeLog(timeLog);
    }

    private TimeLog createNewTimeLog(Activity activity) {
        TimeLogic timeLogic = TimeLogic.newInstance();
        Long createTimeStamp = timeLogic.getDateTimeForDatabaseStorage();
        return new TimeLog(createTimeStamp, createTimeStamp, activity.getActivity(), activity.getActivity());
    }

    // TODO check icons for compatibility
    private void setUpMaterialActivityButtonIcon() {
        Drawable icon = this.mContext.getDrawable(this.mActivity.getIcon());
        this.mMaterialButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, icon, null,
                null);
        icon.setTint(Color.WHITE);
    }

    private void setUpMaterialActivityButtonBasicAttributes() {

        this.mMaterialButton.setId(View.generateViewId());
        this.mMaterialButton.setText(this.mActivity.getActivity());
        this.mMaterialButton.setAutoSizeTextTypeUniformWithConfiguration(1, 12, 1, TypedValue.COMPLEX_UNIT_DIP);
        this.mMaterialButton.setVisibility(View.VISIBLE);
        this.mMaterialButton.setTag(mActivity);
        this.mMaterialButton.setBackgroundColor(Color.parseColor(("#" + this.mActivity.getColor())));
    }

    public MaterialButton getActivityMaterialButton() {
        return mMaterialButton;
    }
}
