package com.example.timetime.ui.activitysummary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.ui.BaseActivityButtons;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment {
    private MaterialButton TEMPLATE_BUTTON;
    Toolbar toolbar;
    private GridLayout mGridLayout;
    private Context mGridContext;
    private ActivityViewModel mActivityViewModel;
    private List<Activity> mActivities;
    private LiveData<Long> currentTime;
    private TimeLogic timeLogic;
    private String mToolBarTime;
    private List<MaterialButton> materialActivityButtons;
    private BaseActivityButtons baseActivityButtons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_time_log, container, false);

        materialActivityButtons = new ArrayList<>();
        mGridLayout = rootView.findViewById(R.id.activity_time_log_gridView);
        mGridContext = mGridLayout.getContext();
        TEMPLATE_BUTTON = rootView.findViewById(R.id.activity_time_log_button_1);

        timeLogic = TimeLogic.newInstance();
        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        mActivities = new ArrayList<Activity>();
        baseActivityButtons = new BaseActivityButtons();
        baseActivityButtons.setUpActivityButtons(getViewLifecycleOwner(),mActivityViewModel,mActivities,mGridContext,mGridLayout,
                TEMPLATE_BUTTON);
        return rootView;
    }

    // factory method for returning an instance of the class
    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }
    // empty constructor
    public ActivityFragment() {
    }
}
