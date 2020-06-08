package com.example.quizapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.quizapp.model.Question;

import java.util.List;

@Dao
public abstract class QuestionDao {
    @Query("SELECT * FROM question WHERE categoryId = :categoryId")
    public abstract List<Question> getQuestionsByCategoryId(String categoryId);

    @Query("SELECT * FROM Question WHERE questionId = :questionId")
    public abstract Question getQuestionById(String questionId);

    @Insert
    public abstract void insertQuestions(Question... question);

    @Query("DELETE FROM Question")
    public abstract void deleteQuestions();
}
