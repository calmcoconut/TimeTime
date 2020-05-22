package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.timetime.database.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Query("DELETE FROM category_table")
    void deleteAll();

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategories();

    @Query("UPDATE category_table SET category = :newCategoryName, category =:newCategoryName,  " +
            "color = :newColor" +
            " WHERE " +
            "category = " +
            ":oldCategoryName")
    void updateCategory(String oldCategoryName, String newCategoryName, String newColor);
}
