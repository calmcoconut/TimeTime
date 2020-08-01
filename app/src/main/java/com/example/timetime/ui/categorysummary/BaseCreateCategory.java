package com.example.timetime.ui.categorysummary;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.database.entity.Category;
import com.example.timetime.ui.BaseCreateAnAction;
import com.example.timetime.viewmodels.CategoryViewModel;

import java.util.Objects;

public abstract class BaseCreateCategory extends BaseCreateAnAction {
    private CategoryViewModel mCategoryViewModel;
    private Category mNewCategory;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        setToolBar();
        setUpColorFab();
        setIrrelevantViews();
        setEditTextHint();
    }

    protected abstract void updateDatabase();

    @Override
    public void setIrrelevantViews() {
        getIconLabel().setVisibility(View.GONE);
        getIconFab().setVisibility(View.GONE);
        getCategoryLabel().setVisibility(View.GONE);
        getCategoryButton().setVisibility(View.GONE);
    }

    @Override
    public void submitButtonOnClickAction() {
        getSubmitFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewCategory = getValuesForDatabaseCategory();
                if (mNewCategory != null) {
                    updateDatabase();
                }
                closeToMain(2);
            }
        });
    }

    private Category getValuesForDatabaseCategory() {
        final String category = getEditTextNameOfItem().getText().toString();
        final String color;
        final int colorRaw = Objects.requireNonNull(getColorFab().getBackgroundTintList()).getDefaultColor();
        color = Integer.toHexString(colorRaw);

        if (isValidName(category)) {
            return new Category(category, color);
        }
        else if (!isValidName(category)) {
            Toast.makeText(this, "Invalid Name!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public Category getNewCategory() {
        return mNewCategory;
    }

    public CategoryViewModel getCategoryViewModel() {
        return mCategoryViewModel;
    }
}
