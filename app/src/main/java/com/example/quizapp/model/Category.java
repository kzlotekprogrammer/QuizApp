package com.example.quizapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Category implements Serializable {
    @PrimaryKey
    @NonNull
    public String categoryId;
    public String categoryName;
    public String categoryDescription;

    public Category(String categoryId, String categoryName, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
}
