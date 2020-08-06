package com.example.timetime.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.timetime.database.dao.ActivityDao;
import com.example.timetime.database.database.AppDatabase;
import com.example.timetime.database.entity.Activity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.example.timetime.db.TestData.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ActivityDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private ActivityDao activityDao;

    @Before
    public void initDb() throws Exception {
        // using in-memory db
        appDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        activityDao = appDatabase.activityDao();
    }

    @After
    public void closeDb() throws Exception {
        appDatabase.close();
    }

    @Test
    public void getActivitiesWhenNoActivitiesInserted() throws InterruptedException {
        List<Activity> activities = LiveDataTestUtil.getValue(activityDao.getAllActivity());
        assertTrue(activities.isEmpty());
    }

    @Test
    public void getActivitiesAfterInsert() throws InterruptedException {
        for (Activity activity : ACTIVITIES_LIST) {
            activityDao.insert(activity);
        }
        List<Activity> activities = LiveDataTestUtil.getValue(activityDao.getAllActivity());
        assertThat(activities.size(), is(ACTIVITIES_LIST.size()));
    }

    @Test
    public void getActivityByName() throws InterruptedException {
        for (Activity activity : ACTIVITIES_LIST) {
            activityDao.insert(activity);
        }
        Activity activity = LiveDataTestUtil.getValue(activityDao.getActivityByName(ACTIVITY_ENTITY1.getActivity()));
        assertThat(activity.getActivity(), is(ACTIVITY_ENTITY1.getActivity()));
        assertThat(activity.getCategory(), is(ACTIVITY_ENTITY1.getCategory()));
        assertThat(activity.getColor(), is(ACTIVITY_ENTITY1.getColor()));
        assertThat(activity.getIcon(), is(ACTIVITY_ENTITY1.getIcon()));
    }

    @Test
    public void updateActivity() throws InterruptedException {
        for (Activity activity : ACTIVITIES_LIST) {
            activityDao.insert(activity);
        }

        activityDao.updateActivity(ACTIVITY_ENTITY2.getActivity(), "UPDATE", "UPDATE", 55, "UPDATE");
        Activity activity = LiveDataTestUtil.getValue(activityDao.getActivityByName("UPDATE"));
        assertNotNull(activity);
        assertThat(activity.getActivity(), is("UPDATE"));
        assertThat(activity.getCategory(), is("UPDATE"));
        assertThat(activity.getIcon(), is(55));
        assertThat(activity.getColor(), is("UPDATE"));
    }

    @Test
    public void updateCategory() throws InterruptedException {
        for (Activity activity : ACTIVITIES_LIST) {
            activityDao.insert(activity);
        }

        activityDao.updateCategory(ACTIVITY_ENTITY2.getCategory(), "UPDATE");
        Activity activity = LiveDataTestUtil.getValue(activityDao.getActivityByName(ACTIVITY_ENTITY2.getActivity()));
        assertNotNull(activity);
        assertThat(activity.getCategory(), is("UPDATE"));
    }

    @Test
    public void deleteActivityByName() throws InterruptedException {
        for (Activity activity : ACTIVITIES_LIST) {
            activityDao.insert(activity);
        }

        activityDao.deleteActivity(ACTIVITY_ENTITY1.getActivity());
        Activity activity = LiveDataTestUtil.getValue(activityDao.getActivityByName(ACTIVITY_ENTITY1.getActivity()));
        assertNull(activity);
    }

}
