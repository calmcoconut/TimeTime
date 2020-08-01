package com.example.timetime.ui.activitySummary;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.timetime.R;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Icon;
import com.example.timetime.ui.BaseCreateAnAction;
import com.example.timetime.ui.categorysummary.SelectCategoryFragment;
import com.example.timetime.ui.dialogs.IconDialogAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseCreateActivity extends BaseCreateAnAction {
    private FragmentManager fragmentManager;
    private List<String> iconList;
    private Activity mNewActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.fragmentManager = getSupportFragmentManager();

        categoryButtonOnClickAction();
        getAllIconsForAdapter();
        iconFabOnClickAction();
        setToolBar();
        setEditTextHint();
        submitButtonOnClickAction();
    }

    protected abstract void setDefaultIcon();

    protected abstract void updateDatabase();

    @Override
    public void submitButtonOnClickAction() {
        getSubmitFab().setOnClickListener(v ->
                {
                    mNewActivity = extractValuesForDatabaseSubmission();
                    if (mNewActivity != null) {
                        updateDatabase();
                    }
                    closeToMain(1);
                }
        );
    }

    private Activity extractValuesForDatabaseSubmission() {
        final String activity = getEditTextNameOfItem().getText().toString();
        final String category = getCategoryButton().getText().toString();
        final Integer icon = (Integer) getIconFab().getTag();
        final int colorRaw = Objects.requireNonNull(getColorFab().getBackgroundTintList()).getDefaultColor();
        final String color = Integer.toHexString(colorRaw);

        if (isValidName(activity) && isValidCategoryType(category) && isValidIcon(icon)) {
            return new Activity(activity, category, icon, color);
        }
        else {
            toastMessageForInvalidSubmission(activity, category, icon);
            return null;
        }
    }

    private void toastMessageForInvalidSubmission(String activity, String category, Integer icon) {
        if (!isValidName(activity)) {
            Toast.makeText(this, "Invalid Name!", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidCategoryType(category)) {
            Toast.makeText(this, "Invalid Category!", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidIcon(icon)) {
            Toast.makeText(this, "Invalid Icon!", Toast.LENGTH_SHORT).show();
        }
    }

    private void categoryButtonOnClickAction() {
        getCategoryButton().setOnClickListener(v -> launchSelectCategoryFragment()
        );
    }

    private void launchSelectCategoryFragment() {
        final String[] newCategory = new String[1];
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();

        SelectCategoryFragment fragment = SelectCategoryFragment.newInstance(v ->
        {
            newCategory[0] = (String) v.getTag();
            getCategoryButton().setText(newCategory[0]);
            getCategoryFragmentView().setVisibility(View.GONE);

            setToolBar();
            fragmentManager.popBackStack();
        });
        setSelectCategoryToolBar();
        getCategoryFragmentView().setVisibility(View.VISIBLE);
        fragmentTransaction.add(getCategoryFragmentView().getId(), fragment).addToBackStack("categorySelect").commit();
    }

    private void setSelectCategoryToolBar() {
        getToolbar().setTitle("Select Category");
    }

    private void iconFabOnClickAction() {
        getIconFab().setOnClickListener(v -> createIconDialog());
    }

    private boolean isValidCategoryType(String categorySelected) {
        return categorySelected != null && !categorySelected.equals(getString(R.string.create_edit_category_button_title));
    }

    private boolean isValidIcon(@Nullable Integer icon) {
        return icon != null;
    }

    private void createIconDialog() {
        GridView gridView = new GridView(this);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        IconDialogAdapter adapter = new IconDialogAdapter(this,
                android.R.layout.simple_list_item_1,
                iconList,
                getIconFab()
        );
        gridView.setAdapter(adapter);
        setIconOnClickAction(gridView);

        gridView.setNumColumns(5);
        gridView.setHorizontalSpacing(1);
        builder.setView(gridView)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void setIconOnClickAction(GridView gridView) {
        gridView.setOnItemClickListener((parent, view, position, id) -> {
        });
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("Edit Activity Name");
    }

    @Override
    public void setIrrelevantViews() {
    }

    private void getAllIconsForAdapter() {
        getActivityViewModel().getAllIcons().observe(this, icons -> {
            if (icons != null) {
                List<String> strList = new ArrayList<>();
                for (Icon icon : icons) {
                    strList.add(String.valueOf(icon.getIcon()));
                }
                iconList = strList;
            }
        });
    }

    @Override
    public void onBackPressed() {
        getCategoryFragmentView().setVisibility(View.GONE);
        super.onBackPressed();
    }

    // getters
    public Activity getNewActivity() {
        return mNewActivity;
    }

}
