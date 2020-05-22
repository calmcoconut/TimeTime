package com.example.timetime.ui.activitySummary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import com.example.timetime.R;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Icon;
import com.example.timetime.ui.BaseCreateCategoryOrActivity;
import com.example.timetime.ui.categorysummary.SelectCategoryFragment;
import com.example.timetime.ui.dialogs.IconDialogAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseCreateActivity extends BaseCreateCategoryOrActivity {
    private FragmentManager fragmentManager;
    private List<String> iconList;
    private Activity mNewActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.fragmentManager = getSupportFragmentManager();

        setUpCategoryButton();
        getAllIconsForAdapter();
        setUpIconFab();
        setToolBar();
        setEditTextHint();
        submitButtonAction();
    }

    protected abstract void setDefaultIcon();

    protected abstract void updateDatabase();

    @Override
    public void submitButtonAction() {
        getSubmitFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewActivity = getValuesForDatabaseActivity();
                if (mNewActivity != null) {
                    updateDatabase();
                }
                closeToMain();
            }
        });
    }

    public Activity getValuesForDatabaseActivity() {
        final String activity = getEditTextNameOfItem().getText().toString();
        final String category = getCategoryButton().getText().toString();
        final Integer icon = (Integer) getIconFab().getTag();
        final String color;
        final int colorRaw = Objects.requireNonNull(getColorFab().getBackgroundTintList()).getDefaultColor();
        color = Integer.toHexString(colorRaw);

        if (isValidName(activity) && isValidCategoryType(category) && isValidIcon(icon)) {
            return new Activity(activity, category, icon, color);
        }
        else if (!isValidName(activity)) {
            Toast.makeText(this, "Invalid Name!", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidCategoryType(category)) {
            Toast.makeText(this, "Invalid Category!", Toast.LENGTH_SHORT).show();
        }

        else if (!isValidIcon(icon)) {
            Toast.makeText(this, "Invalid Icon!", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private void setUpCategoryButton() {
        getCategoryButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCategoryActivity();
            }
        });
    }

    public void setUpIconFab() {
        getIconFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIconDialog();
            }
        });
    }

    public boolean isValidCategoryType(String categorySelected) {
        if (categorySelected == null || categorySelected.equals(getString(R.string.create_edit_category_button_title))) {
            return false;
        }
        return true;
    }

    public boolean isValidIcon(@Nullable Integer icon) {
        if (icon == null) {
            return false;
        }
        return true;
    }

    private void createIconDialog() {
        GridView gridView = new GridView(this);
        ArrayAdapter adapter;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        adapter = new IconDialogAdapter(this, android.R.layout.simple_list_item_1, iconList, getIconFab());
        gridView.setAdapter(adapter);
        setIconOnClick(gridView);

        gridView.setNumColumns(5);
        gridView.setHorizontalSpacing(1);
        builder.setView(gridView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void launchCategoryActivity() {
        final String[] newCategory = new String[1];
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();

        SelectCategoryFragment fragment = SelectCategoryFragment.newInstance(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCategory[0] = (String) v.getTag();
                getCategoryButton().setText(newCategory[0]);
                getCategoryFragmentView().setVisibility(View.GONE);

                setToolBar();
                fragmentManager.popBackStack();
            }
        });
        setSelectCategoryToolBar();
        getCategoryFragmentView().setVisibility(View.VISIBLE);
        fragmentTransaction.add(getCategoryFragmentView().getId(), fragment).addToBackStack("categorySelect").commit();
    }

    private void setIconOnClick(GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    private void setSelectCategoryToolBar() {
        getToolbar().setTitle("Select Category");
    }

    @Override
    public void setEditTextHint() {
        getEditTextNameOfItem().setHint("Edit Activity Name");
    }

    @Override
    public void setIrrelevantViews() {
    }

    private void getAllIconsForAdapter() {
        getActivityViewModel().getAllIcons().observe(this, new Observer<List<Icon>>() {
            @Override
            public void onChanged(List<Icon> icons) {
                if (icons != null) {
                    List<String> strList = new ArrayList<>();
                    for (Icon icon : icons) {
                        strList.add(String.valueOf(icon.getIcon()));
                    }
                    iconList = strList;
                }
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
