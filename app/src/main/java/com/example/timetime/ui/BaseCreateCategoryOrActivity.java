package com.example.timetime.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.entity.Color;
import com.example.timetime.database.entity.Icon;
import com.example.timetime.ui.dialogs.ColorDialogAdapter;
import com.example.timetime.ui.dialogs.IconDialogAdapter;
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
    private ActivityViewModel mActivityViewModel;
    private List<String> colorsList;
    private List<String> iconList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
    }

    public abstract void setToolBar();
    public abstract void setEditTextHint();
    public abstract void setIrrelevantViews();

    public void assignAllViews() {
        toolbar = findViewById(R.id.create_edit_object_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        editTextNameOfItem = findViewById(R.id.create_edit_name);
        colorFab = findViewById(R.id.create_edit_color_fab);
        iconLabel = findViewById(R.id.create_edit_Heading_icon);
        iconFab = findViewById(R.id.create_edit_icon_fab);
        categoryLabel = findViewById(R.id.create_edit_Heading_category);
        categoryButton = findViewById(R.id.create_edit_category_button);
        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        getAllColorsForAdapter();
        getAllIconsForAdapter();
        setUpColorFab(this);
    }

    public void setUpColorFab(Context context) {
        colorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(context, true, false);
            }
        });
    }

    public void setUpIconFab(Context context) {
        iconFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(context, false, true);
            }
        });
    }

    private void createDialog(Context context, boolean isColorDialog, boolean isIconDialog) {
        GridView gridView = new GridView(context);
        ArrayAdapter adapter;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        if (isColorDialog) {
            adapter = new ColorDialogAdapter(this, android.R.layout.simple_list_item_1, colorsList, colorFab, iconFab);
            gridView.setAdapter(adapter);
            setColorOnClick(gridView);
        } else if (isIconDialog) {
            adapter = new IconDialogAdapter(this, android.R.layout.simple_list_item_1, iconList, iconFab);
            gridView.setAdapter(adapter);
            setIconOnClick(gridView);
        }

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

    private void setIconOnClick(GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    private void getAllIconsForAdapter() {
        mActivityViewModel.getAllIcons().observe(this, new Observer<List<Icon>>() {
            @Override
            public void onChanged(List<Icon> colors) {
                if (colors != null) {
                    List<String> strList = new ArrayList<>();
                    for (Icon icon : colors) {
                        strList.add(String.valueOf(icon.getIcon()));
                    }
                    iconList = strList;
                }
            }
        });
    }

}