package com.example.timetime.ui.categorysummary;

import android.os.Bundle;
import com.example.timetime.R;

import java.util.Objects;

public class EditCategoryActivity extends BaseCreateCategory {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
        assignAllViews();
        setUpColorFab();
        setToolBar();
        setEditTextHint();
        setAttributes();
        setIrrelevantViews();
    }

    public void setAttributes() {

    }

    @Override
    public void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Category");
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("Edit Category Name");
    }

    @Override
    public void submitButtonAction() {
        getValuesForDatabaseObject();
    }


    public void getValuesForDatabaseObject() {

    }
}
