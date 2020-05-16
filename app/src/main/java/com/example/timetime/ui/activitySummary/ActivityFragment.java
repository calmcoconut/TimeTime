package com.example.timetime.ui.activitySummary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.timetime.R;
import com.example.timetime.ui.buttons.EditActivityButton;
import com.example.timetime.viewmodels.ActivityViewModel;
import com.google.android.material.button.MaterialButton;

public class ActivityFragment extends Fragment {
    private MaterialButton TEMPLATE_BUTTON;
    Toolbar toolbar;
    private GridLayout mGridLayout;
    private Context mGridContext;
    private ActivityViewModel mActivityViewModel;
    private EditActivityButton baseActivityButtons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_time_log, container, false);

        mGridLayout = rootView.findViewById(R.id.activity_time_log_gridView);
        mGridContext = mGridLayout.getContext();
        TEMPLATE_BUTTON = rootView.findViewById(R.id.activity_button_template);

        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        baseActivityButtons = new EditActivityButton();
        baseActivityButtons.setUpActivityButtons(getViewLifecycleOwner(),mActivityViewModel,mGridContext,mGridLayout,
                TEMPLATE_BUTTON);

        // clean up
        toolbar =  rootView.findViewById(R.id.activity_time_log_toolbar);
        toolbar.setVisibility(View.GONE);
        LinearLayout linearLayout = rootView.findViewById(R.id.linear_layout_parent);
        linearLayout.setVisibility(View.GONE);
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
