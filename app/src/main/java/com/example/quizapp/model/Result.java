package com.example.quizapp.model;

import java.io.Serializable;

public class Result implements Serializable {
    private int goodAnswers;
    private int totalQuestions;

    public Result(int goodAnswers, int totalQuestions) {
        this.goodAnswers = goodAnswers;
        this.totalQuestions = totalQuestions;
    }

    public int getGoodAnswers() {
        return goodAnswers;
    }

    public void setGoodAnswers(int goodAnswers) {
        this.goodAnswers = goodAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
