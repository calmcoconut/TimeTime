package com.example.timetime.ui.activitySummary;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.example.timetime.database.database.FirstDatabase;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;

import java.util.Objects;

public class CreateActivityActivity extends BaseCreateCategoryOrActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar();
        setEditTextHint();
        setDefaultIcon();
    }

    @Override
    public void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Activity");
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("New Activity Name");
    }

    @Override
    public void setIrrelevantViews() {
    }

    @Override
    public void submitButtonAction() {
        getValuesForDatabaseObject();
    }

    public void getValuesForDatabaseObject() {

    }

    private void setDefaultIcon() {
        Drawable defaultIcon = getDrawable(FirstDatabase.INITIAL_ICON);
        getIconFab().setImageDrawable(defaultIcon);
        getIconFab().setColorFilter(Color.WHITE);
    }
}
