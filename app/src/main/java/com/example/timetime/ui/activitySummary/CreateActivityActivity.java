package com.example.timetime.ui.activitySummary;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.example.timetime.utils.DevProperties;

import java.util.Objects;

public class CreateActivityActivity extends BaseCreateActivity {

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


    public void setDefaultIcon() {
        Drawable defaultIcon = getDrawable(DevProperties.INITIAL_ICON);
        getIconFab().setImageDrawable(defaultIcon);
        getIconFab().setColorFilter(Color.WHITE);
        getIconFab().setTag(DevProperties.INITIAL_ICON);
    }

    @Override
    protected void updateDatabase() {
        getActivityViewModel().insert(getNewActivity());
    }
}
