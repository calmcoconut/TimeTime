package com.example.timetime.ui.activitySummary;

import com.example.timetime.ui.BaseCreateCategoryOrActivity;

public abstract class BaseCreateActivity extends BaseCreateCategoryOrActivity {

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("Edit Activity Name");
    }

    @Override
    public void setIrrelevantViews() {
    }

}
