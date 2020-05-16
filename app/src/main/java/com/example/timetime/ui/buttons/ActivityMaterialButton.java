package com.example.timetime.ui.buttons;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
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
                                  ActivityViewModel activityViewModel) {
        this.mActivityViewModel = activityViewModel;
        ViewGroup.LayoutParams params = template_button.getLayoutParams();
        this.mMaterialButton = new MaterialButton(new ContextThemeWrapper(mContext,
                R.style.activity_log_button),
                null,
                R.style.activity_log_button);
        this.mActivity = mActivity;
        this.mMaterialButton.setLayoutParams(params);
        this.mContext = mContext;
        setUpMaterialActivityButtonBasicAttributes();
        setUpMaterialActivityButtonIcon();
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

    public static class ActivityButtonTimeLogOnClicks {

        public void onClickSubmitTimeLog(MaterialButton materialButton, LifecycleOwner owner,
                                         ActivityViewModel viewModel, Context context) {
            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityButtonTapSubmitTimeLog(v, owner, viewModel, context);
                }
            });
        }

        private void activityButtonTapSubmitTimeLog(View view, LifecycleOwner owner, ActivityViewModel viewModel, Context context) {
            Activity activityObject = (Activity) view.getTag();
            if (view.getTag() == null) {
            } else {
                TimeLog timeLog = createNewTimeLog(owner, viewModel, activityObject);
                if (timeLog.getTimestamp_created().equals(timeLog.getTimestamp_modified())) {
                    Toast.makeText(context, "Wait a minute!", Toast.LENGTH_SHORT).show();
                    launchHomeView(owner, context);
                } else {
                    insertTimeLogIntoDataBase(viewModel, timeLog);
                    launchHomeView(owner, context);
                }
            }
        }

        private TimeLog createNewTimeLog(LifecycleOwner owner, ActivityViewModel viewModel, Activity activity) {
            TimeLogic timeLogic = TimeLogic.newInstance();
            Long modifiedTimeStamp = timeLogic.getDateTimeForDatabaseStorage();

            final Long[] createdTimeStamp = new Long[1];
            viewModel.getLastSinceModified().observe(owner, new Observer<Long>() {
                @Override
                public void onChanged(@Nullable final Long latestModifiedTime) {
                    if (latestModifiedTime == null) {
                    } else {
                        createdTimeStamp[0] = latestModifiedTime;
                    }
                }
            });

            return new TimeLog(createdTimeStamp[0], modifiedTimeStamp, activity.getActivity(), activity.getColor(),
                    activity.getIcon(), activity.getCategory());
        }

        private void launchHomeView(LifecycleOwner owner, Context context) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }

        private void insertTimeLogIntoDataBase(ActivityViewModel viewModel, TimeLog timeLog) {
            viewModel.insertTimeLog(timeLog);
        }
    }

    // TODO make on clicks
    public static class ActivityButtonActivityPageOnClicks {

    }
}
