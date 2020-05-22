package com.example.timetime.ui.activitySummary;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.buttons.EditActivityButton;

import java.util.Objects;

public class EditActivityActivity extends BaseCreateActivity {
    private String mOldActivityName;
    private String mOldCategoryName;
    private int mOldIcon;
    private String mOldColor;
    private Activity mOldActivity;
    private Activity mNewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBar();
        setEditTextHint();
        setAttributes();
        mOldActivity = new Activity(mOldActivityName, mOldCategoryName, mOldIcon, mOldColor);
        submitButtonAction();
    }

    public void setAttributes() {
        mOldActivityName = getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_NAME);
        mOldCategoryName = getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_CATEGORY);
        mOldIcon = getIntent().getIntExtra(EditActivityButton.EXTRA_ACTIVITY_ICON, 0);
        ;
        mOldColor = getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_COLOR);

        int color = Color.parseColor("#" + mOldColor);
        Drawable icon = getDrawable(mOldIcon);
        getEditTextNameOfItem().setText(mOldActivityName);
        getCategoryButton().setText(mOldCategoryName);
        getColorFab().setBackgroundTintList(ColorStateList.valueOf(color));
        getIconFab().setBackgroundTintList(ColorStateList.valueOf(color));
        getIconFab().setImageDrawable(icon);
        getIconFab().setColorFilter(Color.WHITE);
        getIconFab().setTag(mOldIcon);
    }

    @Override
    public void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Activity");
    }



    @Override
    public void submitButtonAction() {
        getSubmitFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewActivity = getValuesForDatabaseActivity();
                if (mNewActivity != null) {
                    updateDatabase();
                }
                closeToMain();
            }
        });
    }

    private void updateDatabase() {
        getActivityViewModel().updateActivity(mOldActivity, mNewActivity);
    }
}
