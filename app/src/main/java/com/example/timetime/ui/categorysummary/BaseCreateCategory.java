package com.example.timetime.ui.categorysummary;

import android.view.View;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;

public abstract class BaseCreateCategory extends BaseCreateCategoryOrActivity {

    @Override
    public void setIrrelevantViews() {
        getIconLabel().setVisibility(View.GONE);
        getIconFab().setVisibility(View.GONE);
        getCategoryLabel().setVisibility(View.GONE);
        getCategoryButton().setVisibility(View.GONE);
    }

    @Override
    public void submitButtonAction() {

    }
}
