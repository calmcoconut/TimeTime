package com.example.timetime.ui.statsSummary;

import androidx.lifecycle.LifecycleOwner;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.utils.DevProperties;
import com.example.timetime.viewmodels.TimeLogViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PieChartFactory {
    private static Long fromDate;
    private static Long toDate;
    private static List<TimeLog> timeLogs;
    private static List<PieEntry> entries;
    private static HashMap<String, Float> activitiesMap;
    private static HashMap<String, Float> categoriesMap;
    private static PieDataSet pieDataSet;
    private static PieData pieData;

    private static void createPie(LifecycleOwner lifecycleOwner, PieChart pieChart, Long fromDate,
                                  Long toDate, TimeLogViewModel timeLogViewModel) {
        if (!fromDate.equals(PieChartFactory.fromDate) || !toDate.equals(PieChartFactory.toDate)) {
            PieChartFactory.fromDate = fromDate;
            PieChartFactory.toDate = toDate;
            getTimeLogs(lifecycleOwner, timeLogViewModel);
            cleanTimeLogs();
        }
        if (timeLogs == null) {
            getTimeLogs(lifecycleOwner, timeLogViewModel);
            cleanTimeLogs();
        }
        setupPieAttributes(pieChart);
    }

    public static void createActivityPieChart(LifecycleOwner lifecycleOwner, PieChart pieChart, Long fromDate,
                                              Long toDate, TimeLogViewModel timeLogViewModel) {

        createPie(lifecycleOwner, pieChart, fromDate, toDate, timeLogViewModel);
        cleanTimeLogs();
        setUpActivityPieData();
        showPie(pieChart);
    }

    public static void createCategoryPieChart(LifecycleOwner lifecycleOwner, PieChart pieChart, Long fromDate,
                                              Long toDate, TimeLogViewModel timeLogViewModel) {

        createPie(lifecycleOwner, pieChart, fromDate, toDate, timeLogViewModel);
        setUpCategoryPieData();
        showPie(pieChart);
    }


    private static void showPie(PieChart pieChart) {
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private static void setupPieAttributes(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        Description description = pieChart.getDescription();
        description.setText("\ndata shown in percentages.\nExpect rework in newer version");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
    }

    private static void setUpActivityPieData() {
        entries = new ArrayList<>();
        activitiesMap.forEach((k, v) -> entries.add(new PieEntry(v, k)));
        pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
    }

    private static void setUpCategoryPieData() {
        entries = new ArrayList<>();
        categoriesMap.forEach((k, v) -> entries.add(new PieEntry(v, k)));
        pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
    }

    private static void cleanTimeLogs() {
        TimeLogic timeLogic = TimeLogic.newInstance();
        activitiesMap = new HashMap<>();
        categoriesMap = new HashMap<>();

        if (timeLogs != null) {
            for (TimeLog timeLog : timeLogs) {
                // if the create date is less than the fromDate, modify to only observe time we are interested in
                if (!timeLog.getActivity().equals(DevProperties.WELCOME_TIME_LOG)) {
                    if (timeLog.getTimestamp_created() < fromDate) {
                        timeLog.setTimestamp_created(fromDate);
                    }
                    if (timeLog.getTimestamp_modified() > toDate) {
                        timeLog.setTimestamp_modified(toDate);
                    }
                    Float totalTimeSpent = (float) timeLogic.getMinutesBetweenTwoTimeStamps(timeLog.getTimestamp_created(),
                            timeLog.getTimestamp_modified());

                    addTimeToMap(timeLog, totalTimeSpent);
                }
            }
        }
    }

    private static void addTimeToMap(TimeLog timeLog, Float timeSpent) {
        String activity = timeLog.getActivity();
        String category = timeLog.getCategory();

        if (activitiesMap.containsKey(activity) && categoriesMap.containsKey(category)) {
            Float newActivityValue = activitiesMap.get(activity) + timeSpent;
            Float newCatValue = categoriesMap.get(category) + timeSpent;
            activitiesMap.put(activity, newActivityValue);
            categoriesMap.put(activity, newCatValue);
        }
        else {
            activitiesMap.putIfAbsent(activity, timeSpent);
            categoriesMap.putIfAbsent(category, timeSpent);
        }
    }

    private static void getTimeLogs(LifecycleOwner lifecycleOwner, TimeLogViewModel timeLogViewModel) {
        timeLogViewModel.getTimeLogsFromDayToDay(fromDate, toDate).observe(lifecycleOwner, timeLogsList -> {
            if (timeLogsList == null) {
                getTimeLogs(lifecycleOwner, timeLogViewModel);
            }
            else {
                timeLogs = timeLogsList;
            }
        });
    }
}
