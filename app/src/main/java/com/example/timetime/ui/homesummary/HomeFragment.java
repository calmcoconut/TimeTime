package com.example.timetime.ui.homesummary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.viewmodels.TimeLogViewModel;

public class HomeFragment extends Fragment implements TimeLogListAdapter.TimeLogCardListener {
    private TimeLogViewModel mTimeLogViewModel;
    private TimeLogListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        startRecyclerForTimeLogs(rootView);
        return rootView;
    }

    private void startRecyclerForTimeLogs(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_root);

        adapter = new TimeLogListAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTimeLogViewModel = new ViewModelProvider(this).get(TimeLogViewModel.class);
        mTimeLogViewModel.getAllTimeLogs().observe(getViewLifecycleOwner(), adapter::setTimeLogs);
    }

    // factory method for returning an instance of the class
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    // empty constructor needed for fragment
    public HomeFragment() {
    }

    @Override
    public void onTimeLogClick(int position) {
        // callback for item clicked in the recycler
        Toast.makeText(getContext(), "timeLog activity is " + adapter.getTimeCard(position).getActivity(),
                Toast.LENGTH_SHORT).show();
        TimeLog timeLog = adapter.getTimeCard(position);
        Intent intent = new Intent(getContext(), EditTimeLogActivity.class);
        intent.putExtra("old_time_log_id", timeLog.getTimeLogId());
        intent.putExtra("old_time_log_created_time", timeLog.getTimestamp_created());
        intent.putExtra("old_time_log_modified_time", timeLog.getTimestamp_modified());
        intent.putExtra("old_time_log_activity", timeLog.getActivity());
        startActivity(intent);
    }
}