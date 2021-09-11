package com.example.timetime.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.entity.TimeLog;
import io.reactivex.Single;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TimeLogViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    // cached data
    private LiveData<List<TimeLog>> mAllTimeLogs;
    private LiveData<Long> mostRecentTimeLogTimeStamp;

    public TimeLogViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllTimeLogs = mAppRepository.getAllTimeLogs();
        mostRecentTimeLogTimeStamp = mAppRepository.getMostRecentTimeLogTimeStamp();
    }

    // getters
    public LiveData<List<TimeLog>> getAllTimeLogs() {
        return mAllTimeLogs;
    }

    public LiveData<List<TimeLog>> getTimeLogsFromDayToDay(Long fromDate, Long toDate) {
        return mAppRepository.getTimeLogsFromDayToDay(fromDate, toDate);
    }

    public LiveData<Long> getMostRecentTimeLogTimeStamp() {
        return mostRecentTimeLogTimeStamp;
    }

    public Single<TimeLog> getMostRecentTimeLogSingle() {
        return mAppRepository.getMostRecentTimeLogSingle();
    }

    // inserters
    public void insertTimeLog(TimeLog timeLog) throws ExecutionException, InterruptedException {
        mAppRepository.insertTimeLog(timeLog);
    }

    //updaters

    public void updateTimeLogById(TimeLog oldTimeLog, TimeLog newTimeLog) {
        mAppRepository.updateTimeLogById(oldTimeLog, newTimeLog);
    }

    // deletion

    public void deleteTimeLog(Long timeLogId) {
        mAppRepository.deleteTimeLog(timeLogId);
    }
}
