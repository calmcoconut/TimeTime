package com.example.timetime.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.timetime.R;
import com.example.timetime.ui.activitySummary.ActivityFragment;

public class HelpFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        return rootView;
    }

    // factory method for returning an instance of the class
    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }
    // empty constructor
    public HelpFragment() {
    }
}
