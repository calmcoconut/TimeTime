package com.example.timetime.ui.homesummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.viewmodels.TimeLogViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private TimeLogViewModel mTimeLogViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        startRecyclerForTimeLogs(rootView);
        return rootView;
    }

    private void startRecyclerForTimeLogs(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_root);

        final TimeLogListAdapter adapter = new TimeLogListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTimeLogViewModel = new ViewModelProvider(this).get(TimeLogViewModel.class);
        mTimeLogViewModel.getAllTimeLogs().observe(getViewLifecycleOwner(),
                new Observer<List<TimeLog>>() {
                    @Override
                    public void onChanged(@Nullable final List<TimeLog> timeLogs) {
                        adapter.setTimeLogs(timeLogs);
                    }
                });
    }

    // factory method for returning an instance of the class
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    // empty constructor needed for fragment
    public HomeFragment() {
    }
}
