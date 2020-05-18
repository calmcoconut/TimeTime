package com.example.timetime.ui.categorysummary;

import android.os.Bundle;
import android.view.View;
import com.example.timetime.R;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;

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

    @Override
    public void getValuesForDatabaseObject() {

    }
}
