package com.example.timetime.ui.categorysummary;

import android.content.res.ColorStateList;
import android.os.Bundle;
import com.example.timetime.database.entity.Category;

import java.util.Objects;

public class EditCategoryActivity extends BaseCreateCategory {
    private Category mOldCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToPreviousCategoryAttributes();
        submitButtonOnClickAction();
    }

    private void setToPreviousCategoryAttributes() {
        String oldColor;
        String oldCatName = getIntent().getStringExtra(HomeCategoryFragment.EXTRA_CATEGORY_NAME);
        int colorRaw = getIntent().getIntExtra(HomeCategoryFragment.EXTRA_CATEGORY_COLOR, 0);
        oldColor = Integer.toHexString(colorRaw);

        getEditTextNameOfItem().setText(oldCatName);
        getColorFab().setBackgroundTintList(ColorStateList.valueOf(colorRaw));
        this.mOldCategory = new Category(Objects.requireNonNull(oldCatName), oldColor);
    }

    @Override
    protected void updateDatabase() {
        getCategoryViewModel().updateCategory(mOldCategory, getNewCategory());
    }

    @Override
    public void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Category");
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("Edit Category Name");
    }

}
