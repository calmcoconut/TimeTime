package com.example.timetime.ui.activitysummary;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.timetime.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
        CharSequence[] strs = {"test1","test2","test3","test4"};
        DialogInterface.OnClickListener on = new DialogInterface.OnClickListener() {
            /**
             * This method will be invoked when a button in the dialog is clicked.
             *
             * @param dialog the dialog that received the click
             * @param which  the button that was clicked (ex.
             *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        new MaterialAlertDialogBuilder(context)
                .setTitle("test")
                .setView(R.layout.dialog_recycler)
                .setSingleChoiceItems(strs,1, on)
                .show();
    }
}