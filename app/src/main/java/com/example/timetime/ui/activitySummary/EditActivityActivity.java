package com.example.timetime.ui.activitySummary;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.buttons.EditActivityButton;

public class EditActivityActivity extends BaseCreateActivity {
    private String mOldActivityName;
    private String mOldCategoryName;
    private int mOldIcon;
    private String mOldColor;
    private Activity mOldActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityToEditExtras();
        setToPreviousActivityAttributes();
        mOldActivity = new Activity(mOldActivityName, mOldCategoryName, mOldIcon, mOldColor);
    }

    private void getActivityToEditExtras() {
        mOldActivityName = getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_NAME);
        mOldCategoryName = getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_CATEGORY);
        mOldColor = getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_COLOR);
    }

    private void setToPreviousActivityAttributes() {
        int color = Color.parseColor("#" + mOldColor);

        setDefaultIcon();
        getEditTextNameOfItem().setText(mOldActivityName);
        getCategoryButton().setText(mOldCategoryName);
        getColorFab().setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @Override
    public void setDefaultIcon() {
        mOldIcon = getIntent().getIntExtra(EditActivityButton.EXTRA_ACTIVITY_ICON, 0);
        Drawable icon = getDrawable(mOldIcon);
        int color = Color.parseColor("#" + mOldColor);

        getIconFab().setBackgroundTintList(ColorStateList.valueOf(color));
        getIconFab().setImageDrawable(icon);
        getIconFab().setColorFilter(Color.WHITE);
        getIconFab().setTag(mOldIcon);
    }

    @Override
    public void setToolBar() {
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle("Edit Activity");
        }

    }

    protected void updateDatabase() {
        getActivityViewModel().updateActivity(mOldActivity, getNewActivity());
    }
}
