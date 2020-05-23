package com.example.timetime.ui.statsSummary;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;

public class StatsFragment extends Fragment {
    View inflatedView;
    MaterialButton materialButtonFromSelection;
    MaterialButton materialButtonToSelection;
    String defaultFromLabel;
    String defaultToLabel;
    Chip chipActivity;
    Chip chipCategory;
    MaterialStyledDatePickerDialog fromDatePickerDialog;
    MaterialStyledDatePickerDialog toDatePickerDialog;
    TimeLogic timeLogic;
    PieChart pieChart;
    ActivityViewModel activityViewModel;
    Long fromDate;
    Long toDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        inflatedView = rootView;
        assignViews();
        timeLogic = TimeLogic.newInstance();
        setDatePicker();
        assignOnClickCalenderDialogFrom();
        assignOnClickCalenderDialogTo();
        assignOnClickActivityChip();
        assignOnClickCategoryChip();

        return rootView;
    }

    private void updatePie() {
        if (isPieAttributesValid()) {
            Toast.makeText(inflatedView.getContext(), "valid", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPieAttributesValid() {
        boolean valid = false;
        if (chipActivity.isChecked() || chipCategory.isChecked()) {
            if (!materialButtonFromSelection.getText().toString().equals(defaultFromLabel) && !materialButtonToSelection.getText().toString().equals(defaultToLabel)) {
                valid = true;
            }
        }
        return valid;
    }

    private void setDatePicker() {
        fromDatePickerDialog = new MaterialStyledDatePickerDialog(inflatedView.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fromDate = timeLogic.getLongForDataBaseFromInts(year, month, dayOfMonth);
                setSelectionButtonText(true);
                updatePie();
            }
        }
                , timeLogic.getIntYear(), timeLogic.getIntMonth(), timeLogic.getIntDayOfMonth());

        toDatePickerDialog = new MaterialStyledDatePickerDialog(inflatedView.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        toDate = timeLogic.add23Hours59MinutesToLong(
                                timeLogic.getLongForDataBaseFromInts(year, month,dayOfMonth)
                        );
                        setSelectionButtonText(false);
                        updatePie();
                    }
                }
                , timeLogic.getIntYear(), timeLogic.getIntMonth(), timeLogic.getIntDayOfMonth());
    }

    private void setSelectionButtonText(boolean isFromButton) {
        String humanDateStr;
        if (isFromButton) {
            humanDateStr = timeLogic.getHumanFormattedLongDateFromDatabase(fromDate);
            materialButtonFromSelection.setText(humanDateStr);
        }
        else {
            humanDateStr = timeLogic.getHumanFormattedLongDateFromDatabase(toDate);
            materialButtonToSelection.setText(humanDateStr);
        }
    }

    private void assignOnClickActivityChip() {
        chipActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if selected, deselect category
                chipCategory.setChecked(false);
                updatePie();
            }
        });
    }

    private void assignOnClickCategoryChip() {
        chipCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if selected, deselect activity
                chipActivity.setChecked(false);
                updatePie();
            }
        });
    }

    private void assignOnClickCalenderDialogFrom() {
        materialButtonFromSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
    }

    private void assignOnClickCalenderDialogTo() {
        materialButtonToSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });
    }

    private void assignViews() {
        materialButtonFromSelection = inflatedView.findViewById(R.id.fragment_stats_from_button);
        materialButtonToSelection = inflatedView.findViewById(R.id.fragment_stats_to_button);
        defaultFromLabel = materialButtonFromSelection.getText().toString();
        defaultToLabel = materialButtonToSelection.getText().toString();
        chipActivity = inflatedView.findViewById(R.id.fragment_stats_activity_chip);
        chipCategory = inflatedView.findViewById(R.id.fragment_stats_category_chip);
        pieChart = inflatedView.findViewById(R.id.fragment_stats_pie_chart);
        activityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
    }

    // factory method for returning an instance of the class
    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    // empty constructor
    public StatsFragment() {
    }
}
