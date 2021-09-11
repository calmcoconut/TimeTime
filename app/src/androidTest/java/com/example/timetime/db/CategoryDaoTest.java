package com.example.timetime.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.timetime.database.dao.CategoryDao;
import com.example.timetime.database.database.AppDatabase;
import com.example.timetime.database.entity.Category;
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

    @Test
    public void getCategoryWhenNoCategoryInserted() throws InterruptedException {
        List<Category> category = LiveDataTestUtil.getValue(categoryDao.getAllCategories());
        assertTrue(category.isEmpty());
    }

    @Test
    public void getCategoriesAfterInsertion() throws InterruptedException {
        for (Category category : CATEGORIES_LIST) {
            categoryDao.insert(category);
        }
        List<Category> categories = LiveDataTestUtil.getValue(categoryDao.getAllCategories());

        assertThat(categories.size(), is(CATEGORIES_LIST.size()));
    }

    @Test
    public void getCategoryByName() {
        for (Category category : CATEGORIES_LIST) {
            categoryDao.insert(category);
        }
        Category category = categoryDao.getCategoryByName(CATEGORY_ENTITY1.getCategory());

        assertThat(category.getCategory(), is(CATEGORY_ENTITY1.getCategory()));
        assertThat(category.getColor(), is(CATEGORY_ENTITY1.getColor()));
    }

    @Test
    public void updateCategory() {
        for (Category category : CATEGORIES_LIST) {
            categoryDao.insert(category);
        }
        categoryDao.updateCategory(CATEGORY_ENTITY2.getCategory(), "UPDATED", "UPDATED");

        Category category = categoryDao.getCategoryByName("UPDATED");
        assertThat(category.getCategory(), is("UPDATED"));
        assertThat(category.getColor(), is("UPDATED"));
    }

    @Test
    public void deleteCategoryByName() throws InterruptedException {
        for (Category category : CATEGORIES_LIST) {
            categoryDao.insert(category);
        }
        categoryDao.deleteCategory(CATEGORY_ENTITY1.getCategory());

        Category category = categoryDao.getCategoryByName(CATEGORY_ENTITY1.getCategory());
        assertNull(category);
    }

}
