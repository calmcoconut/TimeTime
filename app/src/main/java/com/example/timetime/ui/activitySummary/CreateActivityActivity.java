package com.example.timetime.ui.activitySummary;

import android.os.Bundle;
import com.example.timetime.R;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;

import java.util.Objects;

public class CreateActivityActivity extends BaseCreateCategoryOrActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
        assignAllViews();
        setUpColorFab(this);
        setUpIconFab(this);
        setToolBar();
    }

    @Override
    public void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Activity");
    }

    @Override
    public void setEditTextHint() {
    }

    @Override
    public void setIrrelevantViews() {
    }
}
