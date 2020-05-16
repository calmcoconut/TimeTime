package com.example.timetime.ui.activitySummary;

import android.os.Bundle;
import com.example.timetime.R;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;

public class CreateActivityActivity extends BaseCreateCategoryOrActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
        assignAllViews();
        setUpColorFab(this);
        setUpIconFab(this);
    }

    @Override
    public void setToolBar() {
    }

    @Override
    public void setEditTextHint() {
    }

    @Override
    public void setIrrelevantViews() {
    }
}
