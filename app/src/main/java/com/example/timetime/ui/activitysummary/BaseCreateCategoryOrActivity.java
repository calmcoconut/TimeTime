package com.example.timetime.ui.activitysummary;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.timetime.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public abstract class BaseCreateCategoryOrActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTextNameOfItem;
    private FloatingActionButton colorFab;
    private TextView iconLabel;
    private FloatingActionButton iconFab;
    private TextView categoryLabel;
    private MaterialButton categoryButton;

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
    }

    public void setUpColorFab() {
        colorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createFragment();
//                launchFragment();
            }
        });
    }
}
