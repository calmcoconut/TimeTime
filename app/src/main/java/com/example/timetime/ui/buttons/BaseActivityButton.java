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
import androidx.lifecycle.Observer;
import com.example.timetime.R;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public abstract class BaseActivityButton {
    private ActivityViewModel activityViewModel;
    private LifecycleOwner owner;
    private Context context;
    private MaterialButton TEMPLATE_BUTTON;
    private List<Activity> activitiesList;

    public abstract void setMaterialButtonOnClick(MaterialButton materialButton);

    public abstract void setMaterialButtonOnLongClick(MaterialButton materialButton);

    public void setUpActivityButtons(LifecycleOwner lifecycleOwner, ActivityViewModel activityViewModel,
                                     Context gridContext, GridLayout gridLayout, MaterialButton TEMPLATE_BUTTON) {
        // TODO assure that minimum number of activities in the database is 1 or THIS WILL LOOP FOREVER.
        this.TEMPLATE_BUTTON = TEMPLATE_BUTTON;
        this.owner = lifecycleOwner;
        this.activityViewModel = activityViewModel;
        this.context = gridContext;
        activityViewModel.getAllActivities().observe(lifecycleOwner, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                activitiesList = activities;
                if (activitiesList == null) {
                    setUpActivityButtons(lifecycleOwner, activityViewModel, gridContext, gridLayout, TEMPLATE_BUTTON);
                }
                else {
                    for (Activity activity : activities) {
                        MaterialButton materialButton =
                                new NewActivityMaterialButton(activity, context).getActivityMaterialButton();
                        setMaterialButtonOnClick(materialButton);
                        gridLayout.addView(materialButton);
                    }
                }
            }
        });
    }

    public Context getContext() {
        return context;
    }

    public ActivityViewModel getActivityViewModel() {
        return activityViewModel;
    }

    public LifecycleOwner getOwner() {
        return owner;
    }

    private class NewActivityMaterialButton {
        private Activity mActivity;
        private MaterialButton mMaterialButton;
        private Context mContext;

        public NewActivityMaterialButton(Activity mActivity, Context mContext) {
            ViewGroup.LayoutParams params = TEMPLATE_BUTTON.getLayoutParams();
            this.mMaterialButton = new MaterialButton(new ContextThemeWrapper(mContext, R.style.activity_log_button),
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
    }
}
