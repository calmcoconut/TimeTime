package com.example.timetime.ui.categorysummary;

import android.os.Bundle;
import com.example.timetime.R;

import java.util.Objects;

public class CreateCategoryActivity extends BaseCreateCategory {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
        assignAllViews();
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
    public void submitButtonAction() {
        getValuesForDatabaseObject();
    }


    public void getValuesForDatabaseObject() {

    }
}
