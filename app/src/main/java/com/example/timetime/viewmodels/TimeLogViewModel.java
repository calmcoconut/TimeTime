package com.example.timetime.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.TimeLog;

import java.util.List;

public class TimeLogViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    // cached data
    private LiveData<List<TimeLog>> mAllTimeLogs;
    private LiveData<TimeLog> mostRecentTimeLog;

    public TimeLogViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllTimeLogs = mAppRepository.getAllTimeLogs();
        mostRecentTimeLog = mAppRepository.getMostRecentTimeLog();
    }

    public LiveData<List<TimeLog>> getAllTimeLogs() {
        return mAllTimeLogs;
    }

    public LiveData<TimeLog> getMostRecentTimeLog() {
        return mostRecentTimeLog;
    }

    public LiveData<List<TimeLog>> getTimeLogsFromDayToDay(Long fromDate, Long toDate) {
        return mAppRepository.getTimeLogsFromDayToDay(fromDate, toDate);
    }

    public void insert(TimeLog timeLog) {
        mAppRepository.insertTimeLog(timeLog);
    }
}
