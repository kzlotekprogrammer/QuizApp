package com.example.quizapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.quizapp.model.Category;

import java.util.List;

@Dao
public abstract class CategoryDao {
    @Query("SELECT * FROM Category")
    public abstract List<Category> getAllCategories();

    @Insert
    public abstract void insertCategories(Category... categories);

    @Query("DELETE FROM Category")
    public abstract void deleteAllCategories();
}
