package com.example.timetime.ui.categorysummary;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.example.timetime.R;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;
import com.example.timetime.ui.buttons.EditActivityButton;

import java.util.Objects;

public class EditCategoryActivity extends BaseCreateCategoryOrActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
        assignAllViews();
        setUpColorFab(this);
        setUpIconFab(this);
        setToolBar();
        setEditTextHint();
        setAttributes();
    }

    public void setAttributes() {
        int color = Color.parseColor("#" + getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_COLOR));
        Drawable icon = getDrawable(getIntent().getIntExtra(EditActivityButton.EXTRA_ACTIVITY_ICON,0));
        getEditTextNameOfItem().setText(getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_NAME));
        getCategoryButton().setText(getIntent().getStringExtra(EditActivityButton.EXTRA_ACTIVITY_CATEGORY));
        getColorFab().setBackgroundTintList(ColorStateList.valueOf(color));
        getIconFab().setBackgroundTintList(ColorStateList.valueOf(color));
        getIconFab().setImageDrawable(icon);
    }

    @Override
    public void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Activity");
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("Edit Activity Name");
    }

    @Override
    public void setIrrelevantViews() {
    }
}
