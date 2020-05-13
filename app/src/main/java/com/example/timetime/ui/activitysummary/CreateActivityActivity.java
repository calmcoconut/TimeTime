package com.example.timetime.ui.activitysummary;

import android.os.Bundle;
import com.example.timetime.R;

public class CreateActivityActivity extends BaseCreateCategoryOrActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
        assignAllViews();
    }

    @Override
    void setUpToolBar() {

    }

    @Override
    void setEditTextHint() {

    }

    @Override
    void hideIrrelevantViews() {

    }
}
