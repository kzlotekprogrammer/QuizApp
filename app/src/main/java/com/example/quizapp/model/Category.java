package com.example.quizapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Category implements Serializable {
    @PrimaryKey
    public int categoryId;
    public String categoryDescription;

    public Category(int categoryId, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryDescription = categoryDescription;
    }
}
