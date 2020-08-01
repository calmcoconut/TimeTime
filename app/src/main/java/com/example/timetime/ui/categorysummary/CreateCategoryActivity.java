package com.example.timetime.ui.categorysummary;

import android.os.Bundle;

import java.util.Objects;

public class CreateCategoryActivity extends BaseCreateCategory {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submitButtonOnClickAction();
    }

    @Override
    protected void updateDatabase() {
        getCategoryViewModel().insert(getNewCategory());
    }

    @Override
    public void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Category");
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("New Category Name");
    }
}
