package com.example.timetime.ui.statsSummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.viewmodels.TimeLogViewModel;
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
    TimeLogViewModel timeLogViewModel;
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
            if (chipActivity.isChecked()) {
                PieChartFactory.createActivityPieChart(this, pieChart, fromDate, toDate, timeLogViewModel);
            }
            else {
                PieChartFactory.createCategoryPieChart(this, pieChart, fromDate, toDate, timeLogViewModel);
            }
        }
    }

    private boolean isPieAttributesValid() {
        boolean valid = false;
        if (chipActivity.isChecked() || chipCategory.isChecked()) {
            if (!materialButtonFromSelection.getText().toString().equals(defaultFromLabel)
                    && !materialButtonToSelection.getText().toString().equals(defaultToLabel)) {
                valid = true;
            }
        }
        return valid;
    }

    private void setDatePicker() {
        int year = timeLogic.getIntYear();
        // picker's month index begins at 0.
        int month = timeLogic.getIntMonth() - 1;
        int day = timeLogic.getIntDayOfMonth();

        fromDatePickerDialog = new MaterialStyledDatePickerDialog(inflatedView.getContext(), (view, year12, month12, dayOfMonth) -> {
            fromDate = timeLogic.getLongForDataBaseFromInts(year12, month12 + 1, dayOfMonth); // again rectifying month
            setSelectionButtonText(true);
            updatePie();
        }
                , year, month, day);
        toDatePickerDialog = new MaterialStyledDatePickerDialog(inflatedView.getContext(),
                (view, year1, month1, dayOfMonth) -> {
                    toDate = timeLogic.addDayToLong(
                            timeLogic.getLongForDataBaseFromInts(year1, month1 + 1, dayOfMonth)
                    );
                    setSelectionButtonText(false);
                    updatePie();
                }
                , year, month, day);
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
        chipActivity.setOnClickListener(v -> {
            // if selected, deselect category
            chipCategory.setChecked(false);
            updatePie();
        });
    }

    private void assignOnClickCategoryChip() {
        chipCategory.setOnClickListener(v -> {
            // if selected, deselect activity
            chipActivity.setChecked(false);
            updatePie();
        });
    }

    private void assignOnClickCalenderDialogFrom() {
        materialButtonFromSelection.setOnClickListener(v -> fromDatePickerDialog.show());
    }

    private void assignOnClickCalenderDialogTo() {
        materialButtonToSelection.setOnClickListener(v -> toDatePickerDialog.show());
    }

    private void assignViews() {
        materialButtonFromSelection = inflatedView.findViewById(R.id.fragment_stats_from_button);
        materialButtonToSelection = inflatedView.findViewById(R.id.fragment_stats_to_button);
        defaultFromLabel = materialButtonFromSelection.getText().toString();
        defaultToLabel = materialButtonToSelection.getText().toString();
        chipActivity = inflatedView.findViewById(R.id.fragment_stats_activity_chip);
        chipActivity.setChecked(true);
        chipCategory = inflatedView.findViewById(R.id.fragment_stats_category_chip);
        pieChart = inflatedView.findViewById(R.id.fragment_stats_pie_chart);
        timeLogViewModel = new ViewModelProvider(this).get(TimeLogViewModel.class);
    }

    // factory method for returning an instance of the class
    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    // empty constructor
    public StatsFragment() {
    }
}
