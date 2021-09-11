package com.example.timetime.ui.settingsSummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.timetime.MainActivity;
import com.example.timetime.R;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_view, container, false);
        return rootView;
    }

    // factory method for returning an instance of the class
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
    // empty constructor
    public SettingsFragment() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MainActivity.setViewVisibleCallBack();
    }
}
