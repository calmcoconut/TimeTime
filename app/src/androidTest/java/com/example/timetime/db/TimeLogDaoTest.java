package com.example.timetime.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.timetime.database.dao.TimeLogDao;
import com.example.timetime.database.database.AppDatabase;
import com.example.timetime.database.entity.TimeLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.example.timetime.db.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TimeLogDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private TimeLogDao timeLogDao;

    @Before
    public void initDb() throws Exception {
        // using in-memory db
        appDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        timeLogDao = appDatabase.timeLogDao();
    }

    @After
    public void closeDb() throws Exception {
        appDatabase.close();
    }

    @Test
    public void getTimeLogsWhenNoTimeLogsInserted() throws InterruptedException {
        List<TimeLog> timeLogs = LiveDataTestUtil.getValue(timeLogDao.getAllTimeLogs());

        assertTrue(timeLogs.isEmpty());
    }

    @Test
    public void getTimeLogsAfterInsert() throws InterruptedException {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }

        List<TimeLog> timeLogs = LiveDataTestUtil.getValue(timeLogDao.getAllTimeLogs());

        assertThat(timeLogs.size(), is(TIME_LOG_LIST.size()));
    }

    @Test
    public void getMostRecentTimeLog() throws InterruptedException {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }
        TimeLog timeLog = timeLogDao.getMostRecentTimeLogEntry();
        assertNotNull(timeLog);
        assertThat(timeLog.getActivity(),is(TIME_LOG_ENTITY3.getActivity()));
        assertThat(timeLog.getActivityColor(),is(TIME_LOG_ENTITY3.getActivityColor()));
        assertThat(timeLog.getActivityIcon(),is(TIME_LOG_ENTITY3.getActivityIcon()));
        assertThat(timeLog.getCategory(),is(TIME_LOG_ENTITY3.getCategory()));
        TIME_LOG_ENTITY3.setTimeLogId(timeLog.getTimeLogId());
        assertThat(timeLog.getTimeLogId(),is(TIME_LOG_ENTITY3.getTimeLogId()));
        assertThat(timeLog.getTimestamp_created(),is(TIME_LOG_ENTITY3.getTimestamp_created()));
        assertThat(timeLog.getTimestamp_modified(),is(TIME_LOG_ENTITY3.getTimestamp_modified()));
    }

    @Test
    public void getTimeLogById() throws InterruptedException {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }
        TimeLog timeLog = timeLogDao.getTimeLogById(2L);
        assertNotNull(timeLog);
        assertThat(timeLog.getActivity(),is(TIME_LOG_ENTITY2.getActivity()));
        assertThat(timeLog.getActivityColor(),is(TIME_LOG_ENTITY2.getActivityColor()));
        assertThat(timeLog.getActivityIcon(),is(TIME_LOG_ENTITY2.getActivityIcon()));
        assertThat(timeLog.getCategory(),is(TIME_LOG_ENTITY2.getCategory()));
        TIME_LOG_ENTITY2.setTimeLogId(timeLog.getTimeLogId());
        assertThat(timeLog.getTimeLogId(),is(TIME_LOG_ENTITY2.getTimeLogId()));
        assertThat(timeLog.getTimestamp_created(),is(TIME_LOG_ENTITY2.getTimestamp_created()));
        assertThat(timeLog.getTimestamp_modified(),is(TIME_LOG_ENTITY2.getTimestamp_modified()));
    }
}
