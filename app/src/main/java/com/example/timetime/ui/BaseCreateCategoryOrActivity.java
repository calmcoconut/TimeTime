package com.example.timetime.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.MainActivity;
import com.example.timetime.R;
import com.example.timetime.database.entity.Color;
import com.example.timetime.ui.dialogs.ColorDialogAdapter;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseCreateCategoryOrActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTextNameOfItem;
    private FloatingActionButton colorFab;
    private TextView iconLabel;
    private FloatingActionButton iconFab;
    private TextView categoryLabel;
    private MaterialButton categoryButton;
    private MaterialButton submitFab;
    private FragmentContainerView categoryFragmentView;
    private ActivityViewModel mActivityViewModel;
    private List<String> colorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);

        assignAllViews();
        setUpColorFab();
    }

    public abstract void setToolBar();

    public abstract void setEditTextHint();

    public abstract void setIrrelevantViews();

    public abstract void submitButtonAction();

    public void assignAllViews() {
        toolbar = findViewById(R.id.create_edit_object_toolbar);
        setSupportActionBar(getToolbar());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        editTextNameOfItem = findViewById(R.id.create_edit_name);
        colorFab = findViewById(R.id.create_edit_color_fab);
        iconLabel = findViewById(R.id.create_edit_Heading_icon);
        iconFab = findViewById(R.id.create_edit_icon_fab);
        categoryLabel = findViewById(R.id.create_edit_Heading_category);
        categoryButton = findViewById(R.id.create_edit_category_button);
        submitFab = findViewById(R.id.create_edit_submit_fab);
        categoryFragmentView = findViewById(R.id.create_edit_category_fragment);
        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        getAllColorsForAdapter();
        setUpColorFab();
    }

    public void setUpColorFab() {
        colorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createColorDialog();
            }
        });
    }

    private void createColorDialog() {
        GridView gridView = new GridView(this);
        ArrayAdapter adapter;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

        adapter = new ColorDialogAdapter(this, android.R.layout.simple_list_item_1, colorsList, colorFab, iconFab);
        gridView.setAdapter(adapter);
        setColorOnClick(gridView);

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

    private void setColorOnClick(GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getAdapter().getItem(position);
            }
        });
    }

    private void getAllColorsForAdapter() {
        mActivityViewModel.getAllColors().observe(this, new Observer<List<Color>>() {
            @Override
            public void onChanged(List<Color> colors) {
                if (colors != null) {
                    List<String> strList = new ArrayList<>();
                    for (Color color : colors) {
                        strList.add(color.getColor());
                    }
                    colorsList = strList;
                }
            }
        });
    }


    /**
     * @param position choose where to close to... 0 is Home, 1 is Activity, 2 is Category
     */
    public void closeToMain(Integer position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tab", position);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return true;
    }

    // getters

    public Toolbar getToolbar() {
        return toolbar;
    }

    public EditText getEditTextNameOfItem() {
        return editTextNameOfItem;
    }

    public TextView getIconLabel() {
        return iconLabel;
    }

    public TextView getCategoryLabel() {
        return categoryLabel;
    }

    public MaterialButton getCategoryButton() {
        return categoryButton;
    }

    public FloatingActionButton getColorFab() {
        return colorFab;
    }

    public FloatingActionButton getIconFab() {
        return iconFab;
    }

    public MaterialButton getSubmitFab() {
        return submitFab;
    }

    public ActivityViewModel getActivityViewModel() {
        return mActivityViewModel;
    }

    public FragmentContainerView getCategoryFragmentView() {
        return categoryFragmentView;
    }
}