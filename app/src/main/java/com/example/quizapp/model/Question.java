package com.example.quizapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Question implements Serializable {
    @PrimaryKey
    public int questionId;
    public int categoryId;
    public String questionContent;
    public String answer_1;
    public String answer_2;
    public String answer_3;
    public String answer_4;
    public int correctAnswer;

    public Question(int questionId, int categoryId, String questionContent, String answer_1, String answer_2, String answer_3, String answer_4, int correctAnswer) {
        this.questionId = questionId;
        this.categoryId = categoryId;
        this.questionContent = questionContent;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
        this.correctAnswer = correctAnswer;
    }
}
