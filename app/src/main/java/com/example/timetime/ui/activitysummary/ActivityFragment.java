package com.example.timetime.ui.activitysummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.timetime.R;

public class ActivityFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recyclerview_item, container, false);

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
