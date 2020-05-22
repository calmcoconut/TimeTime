package com.example.timetime.ui.categorysummary;

import android.os.Bundle;
import android.view.View;
import com.example.timetime.R;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;

import java.util.Objects;

public class CreateCategoryActivity extends BaseCreateCategoryOrActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
        assignAllViews();
        setUpColorFab(this);
        setUpIconFab(this);
        setEditTextHint();
        setToolBar();
        setIrrelevantViews();
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
        getIconLabel().setVisibility(View.GONE);
        getIconFab().setVisibility(View.GONE);
        getCategoryLabel().setVisibility(View.GONE);
        getCategoryButton().setVisibility(View.GONE);
    }

    @Override
    public void submitButtonAction() {
        getValuesForDatabaseObject();
    }


    public void getValuesForDatabaseObject() {

    }
}
