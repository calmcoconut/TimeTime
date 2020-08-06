package com.example.timetime.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.timetime.database.dao.CategoryDao;
import com.example.timetime.database.database.AppDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CategoryDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private CategoryDao categoryDao;

    @Before
    public void initDb() throws Exception {
        // using in-memory db
        appDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        categoryDao = appDatabase.categoryDao();
    }

    @After
    public void closeDb() throws Exception {
        appDatabase.close();
    }
}
