package com.example.timetime.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.timetime.database.entity.Category;
import com.example.timetime.database.relations.CategoryToTimeLog;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Query("DELETE FROM category_table")
    void deleteAll();

    // getters
    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM category_table WHERE category = :category LIMIT 1")
    Category getCategoryByName(String category);

    @Query("UPDATE category_table SET category = :newCategoryName, category =:newCategoryName,  " +
            "color = :newColor" +
            " WHERE " +
            "category = " +
            ":oldCategoryName")
    void updateCategory(String oldCategoryName, String newCategoryName, String newColor);

    // other
    @Query("DELETE FROM category_table WHERE category = :category")
    void deleteCategory(String category);

    @Transaction
    @Query("SELECT * FROM category_table")
    public List<CategoryToTimeLog> getCategoriesToTimeLogs();
}
