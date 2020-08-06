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
    public void getMostRecentTimeLog() {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }
        TimeLog timeLog = timeLogDao.getMostRecentTimeLogEntry();
        assertNotNull(timeLog);
        assertThat(timeLog.getActivity(), is(TIME_LOG_ENTITY3.getActivity()));
        assertThat(timeLog.getActivityColor(), is(TIME_LOG_ENTITY3.getActivityColor()));
        assertThat(timeLog.getActivityIcon(), is(TIME_LOG_ENTITY3.getActivityIcon()));
        assertThat(timeLog.getCategory(), is(TIME_LOG_ENTITY3.getCategory()));
        TIME_LOG_ENTITY3.setTimeLogId(timeLog.getTimeLogId());
        assertThat(timeLog.getTimeLogId(), is(TIME_LOG_ENTITY3.getTimeLogId()));
        assertThat(timeLog.getTimestamp_created(), is(TIME_LOG_ENTITY3.getTimestamp_created()));
        assertThat(timeLog.getTimestamp_modified(), is(TIME_LOG_ENTITY3.getTimestamp_modified()));
    }

    @Test
    public void getTimeLogById() {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }

        TimeLog timeLog = timeLogDao.getTimeLogById(2L);

        assertNotNull(timeLog);
        assertThat(timeLog.getActivity(), is(TIME_LOG_ENTITY2.getActivity()));
        assertThat(timeLog.getActivityColor(), is(TIME_LOG_ENTITY2.getActivityColor()));
        assertThat(timeLog.getActivityIcon(), is(TIME_LOG_ENTITY2.getActivityIcon()));
        assertThat(timeLog.getCategory(), is(TIME_LOG_ENTITY2.getCategory()));
        TIME_LOG_ENTITY2.setTimeLogId(timeLog.getTimeLogId());
        assertThat(timeLog.getTimeLogId(), is(TIME_LOG_ENTITY2.getTimeLogId()));
        assertThat(timeLog.getTimestamp_created(), is(TIME_LOG_ENTITY2.getTimestamp_created()));
        assertThat(timeLog.getTimestamp_modified(), is(TIME_LOG_ENTITY2.getTimestamp_modified()));
    }

    @Test
    public void getOldestTimeStamp() throws InterruptedException {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }
        Long oldestTimeStamp = LiveDataTestUtil.getValue(timeLogDao.getOldestTimeStamp());
        assertNotNull(oldestTimeStamp);
        assertThat(oldestTimeStamp, is(TIME_LOG_ENTITY1.getTimestamp_created()));
    }

    @Test
    public void getTimeLogsBetween() throws InterruptedException {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }

        List<TimeLog> timeLogs = LiveDataTestUtil.getValue(timeLogDao.getTimeLogsFromDayToDay(1L, 3L));

        assertNotNull(timeLogs);
        assertThat(timeLogs.get(0).getActivity(), is(TIME_LOG_ENTITY1.getActivity()));
        assertThat(timeLogs.get(0).getCategory(), is(TIME_LOG_ENTITY1.getCategory()));
        assertThat(timeLogs.get(1).getActivity(), is(TIME_LOG_ENTITY2.getActivity()));
        assertThat(timeLogs.get(1).getCategory(), is(TIME_LOG_ENTITY1.getCategory()));
    }

    @Test
    public void updateTimeLog() {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }

        TimeLog timeLog = new TimeLog(5L, 6L, "updatedActivity",
                "blue", 2, "updatedCategory");
        timeLogDao.updateTimeLogUsingID(2L, timeLog.getTimestamp_created(), timeLog.getTimestamp_modified(),
                timeLog.getActivity(), timeLog.getCategory(), timeLog.getActivityIcon(), timeLog.getActivityColor());

        TimeLog updatedTimeLog = timeLogDao.getTimeLogById(2L);
        assertNotNull(updatedTimeLog);
        assertThat(updatedTimeLog.getActivity(), is(timeLog.getActivity()));
        assertThat(updatedTimeLog.getActivityColor(), is(timeLog.getActivityColor()));
        assertThat(updatedTimeLog.getActivityIcon(), is(timeLog.getActivityIcon()));
        assertThat(updatedTimeLog.getCategory(), is(timeLog.getCategory()));

        assertThat(updatedTimeLog.getTimeLogId(), is(2L));
        assertThat(updatedTimeLog.getTimestamp_created(), is(timeLog.getTimestamp_created()));
        assertThat(updatedTimeLog.getTimestamp_modified(), is(timeLog.getTimestamp_modified()));
    }

    @Test
    public void updateActivities() {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }
        timeLogDao.updateActivity(TIME_LOG_ENTITY1.getActivity(),"UPDATED","UPDATED",999,"UPDATED");

        TimeLog timeLog = timeLogDao.getTimeLogById(1L);

        assertNotNull(timeLog);
        assertThat(timeLog.getActivity(),is("UPDATED"));
        assertThat(timeLog.getCategory(),is("UPDATED"));
        assertThat(timeLog.getActivityIcon(),is(999));
        assertThat(timeLog.getActivityColor(),is("UPDATED"));
    }

    @Test
    public void updateCategories() {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }
        timeLogDao.updateCategory(TIME_LOG_ENTITY1.getCategory(),"UPDATED");

        TimeLog timeLog = timeLogDao.getTimeLogById(1L);

        assertNotNull(timeLog);
        assertThat(timeLog.getCategory(),is("UPDATED"));
    }

    @Test
    public void updateColors() {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }

        timeLogDao.updateActivityColor(TIME_LOG_ENTITY1.getActivity(),"UPDATED");

        TimeLog timeLog = timeLogDao.getTimeLogById(1L);

        assertNotNull(timeLog);
        assertThat(timeLog.getActivityColor(),is("UPDATED"));
    }

    @Test
    public void updateIcon() {
        for (TimeLog timeLog : TIME_LOG_LIST) {
            timeLogDao.insert(timeLog);
        }

        timeLogDao.updateActivityIcon(TIME_LOG_ENTITY1.getActivity(),55);

        TimeLog timeLog = timeLogDao.getTimeLogById(1L);

        assertNotNull(timeLog);
        assertThat(timeLog.getActivityIcon(),is(55));
    }
}
