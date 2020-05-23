package com.example.timetime.ui.helpSummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.timetime.R;

public class HelpFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_help, container, false);
        return rootView;
    }

    // factory method for returning an instance of the class
    public static HelpFragment newInstance() {
        return new HelpFragment();
    }
    // empty constructor
    public HelpFragment() {
    }
}
