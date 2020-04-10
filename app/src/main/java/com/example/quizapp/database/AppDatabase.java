package com.example.quizapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quizapp.model.Category;
import com.example.quizapp.model.Question;

@Database(entities = {Question.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase = null;

    public abstract QuestionDao questionDao();
    public abstract CategoryDao categoryDao();

    public static AppDatabase getInstance(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "quiz-database").build();
        }

        return appDatabase;
    }
}
