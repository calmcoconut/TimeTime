package com.example.timetime.ui.activitysummary;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.entity.Color;
import com.example.timetime.ui.dialogs.DialogAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_object);
    }

    abstract void setUpToolBar();

    abstract void setEditTextHint();

    abstract void hideIrrelevantViews();

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
        mActivityViewModel.getAllColors().observe(this, new Observer<List<Color>>() {
            @Override
            public void onChanged(List<Color> colors) {
                if (colors !=null) {
                    List<String> strList = new ArrayList<>();
                    for (Color color:colors) {
                        strList.add(color.getColor());
                    }
                    colorsList = strList;
                }
            }
        });
    }

    public void setUpColorFab(Context context) {
        colorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(context);
//                launchFragment();
            }
        });
    }

    public void createDialog(Context context) {
        GridView gridView = new GridView(context);
        DialogAdapter adapter = new DialogAdapter(this, android.R.layout.simple_list_item_1,colorsList);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(5);
        gridView.setHorizontalSpacing(1);
        gridView.setPadding(50, 50, 50, 50);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setView(gridView)
                .setTitle("Test")
                .show();
    }
}