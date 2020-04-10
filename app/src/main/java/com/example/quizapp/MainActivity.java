package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.database.CategoryDao;
import com.example.quizapp.database.QuestionDao;
import com.example.quizapp.model.Category;
import com.example.quizapp.model.Question;
import com.example.quizapp.ui.PlayingActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testPlaying();
    }

    //todo method below will be removed
    private void testPlaying() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CategoryDao categoryDao = AppDatabase.getInstance(MainActivity.this).categoryDao();
                QuestionDao questionDao = AppDatabase.getInstance(MainActivity.this).questionDao();

                categoryDao.deleteAllCategories();
                questionDao.deleteQuestions();

                Category category = new Category(1, randomString(20));
                categoryDao.insertCategories(category);

                for(int i = 0; i < 20; i++) {
                    questionDao.insertQuestions(new Question(i, category.categoryId, randomString(60) + "?", randomString(10), randomString(10), randomString(10),
                            randomString(10), 1));
                }

                startActivity(PlayingActivity.createIntentForQuestionActivity(MainActivity.this, category));
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private String randomString(final int length) {
        StringBuilder s = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            if(random.nextInt() % 4 == 0 && i != 0) {
                s.append(" ");
            }
            s.append((char)(Math.abs(random.nextInt()) % ('Z' - 'A' + 1) + 'A'));
        }
        return s.toString();
    }
}
