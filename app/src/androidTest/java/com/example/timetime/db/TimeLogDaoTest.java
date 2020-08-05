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

import static org.junit.Assert.assertTrue;

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
}
