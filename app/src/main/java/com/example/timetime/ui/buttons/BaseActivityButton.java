package com.example.timetime.ui.buttons;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.LifecycleOwner;
import com.example.timetime.R;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.example.timetime.viewmodels.TimeLogViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Objects;

public abstract class BaseActivityButton {
    public static final int ICON_KEY = 821478213;
    public static final int CAT_KEY = 196534123;
    public static final int COLOR_KEY = 532453552;
    private ActivityViewModel activityViewModel;
    private TimeLogViewModel timeLogViewModel;
    private LifecycleOwner owner;
    private Context context;
    private MaterialButton TEMPLATE_BUTTON;
    private List<Activity> activitiesList;

    public abstract void setMaterialButtonOnClickAction(MaterialButton materialButton);

    public abstract void setMaterialButtonOnLongClickAction(MaterialButton materialButton);

    public void setUpActivityButtons(LifecycleOwner lifecycleOwner, ActivityViewModel activityViewModel,
                                     TimeLogViewModel timeLogViewModel,
                                     Context gridContext, GridLayout gridLayout, MaterialButton TEMPLATE_BUTTON) {
        // TODO assure that minimum number of activities in the database is 1 or THIS WILL LOOP FOREVER.
        this.TEMPLATE_BUTTON = TEMPLATE_BUTTON;
        this.owner = lifecycleOwner;
        this.activityViewModel = activityViewModel;
        this.timeLogViewModel = timeLogViewModel;
        this.context = gridContext;

        verifyActivityLoadedWithObservable(gridLayout);
    }

    private void verifyActivityLoadedWithObservable(GridLayout gridLayout) {
        this.activityViewModel.getAllActivities().observe(this.owner, activities -> {
                    activitiesList = activities;
                    if (activitiesList == null) {
                        setUpActivityButtons(this.owner,
                                this.activityViewModel,
                                this.timeLogViewModel,
                                this.context,
                                gridLayout,
                                this.TEMPLATE_BUTTON);
                    }
                    for (Activity activity : Objects.requireNonNull(activities)) {
                        createActivityButton(gridLayout, activity);
                    }
                }
        );
    }

    private void createActivityButton(GridLayout gridLayout, Activity activity) {
        MaterialButton materialButton = new ActivityMaterialButton(activity, context)
                .getActivityMaterialButton();
        setMaterialButtonOnClickAction(materialButton);
        gridLayout.addView(materialButton);
    }

    // getters
    public Context getContext() {
        return context;
    }

    public ActivityViewModel getActivityViewModel() {
        return activityViewModel;
    }

    public TimeLogViewModel getTimeLogViewModel() {
        return timeLogViewModel;
    }

    public LifecycleOwner getOwner() {
        return owner;
    }


    private class ActivityMaterialButton {
        private Activity mActivity;
        private MaterialButton mMaterialButton;
        private Context mContext;

        public ActivityMaterialButton(Activity mActivity, Context mContext) {
            ViewGroup.LayoutParams params = TEMPLATE_BUTTON.getLayoutParams();

            this.mMaterialButton = new MaterialButton(new ContextThemeWrapper(mContext, R.style.activity_log_button),
                    null,
                    R.style.activity_log_button);
            this.mActivity = mActivity;
            this.mMaterialButton.setLayoutParams(params);
            this.mContext = mContext;

            setUpMaterialActivityButtonBasicAttributes();
            setUpMaterialActivityButtonIcon();
            setTags();
        }

        private void setUpMaterialActivityButtonBasicAttributes() {
            this.mMaterialButton.setId(View.generateViewId());
            this.mMaterialButton.setText(this.mActivity.getActivity());
            this.mMaterialButton.setAutoSizeTextTypeUniformWithConfiguration(1, 12, 1, TypedValue.COMPLEX_UNIT_DIP);
            this.mMaterialButton.setVisibility(View.VISIBLE);
            this.mMaterialButton.setTag(mActivity);
            this.mMaterialButton.setBackgroundColor(Color.parseColor(("#" + this.mActivity.getColor())));
        }

        private void setUpMaterialActivityButtonIcon() {
            Drawable icon = this.mContext.getDrawable(this.mActivity.getIcon());
            this.mMaterialButton
                    .setCompoundDrawablesRelativeWithIntrinsicBounds(null, icon, null, null);
            assert icon != null;
            icon.setTint(Color.WHITE);
        }

        private void setTags() {
            mMaterialButton.setTag(ICON_KEY, this.mActivity.getIcon());
            mMaterialButton.setTag(CAT_KEY, this.mActivity.getCategory());
            mMaterialButton.setTag(COLOR_KEY, this.mActivity.getColor());
        }

        public MaterialButton getActivityMaterialButton() {
            return mMaterialButton;
        }
    }
}
