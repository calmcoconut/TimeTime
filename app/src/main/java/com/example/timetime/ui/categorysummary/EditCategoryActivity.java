package com.example.timetime.ui.categorysummary;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import com.example.timetime.R;
import com.example.timetime.database.entity.Category;
import org.jetbrains.annotations.NotNull;

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
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle("Edit Category");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteCategory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteCategory() {
        getCategoryViewModel().deleteCategory(mOldCategory);
        closeToMain(2);
        Toast.makeText(this, mOldCategory.getCategory() + " deleted successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("Edit Category Name");
    }

}
