package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.database.CategoryDao;
import com.example.quizapp.database.QuestionDao;
import com.example.quizapp.model.Category;
import com.example.quizapp.model.Question;
import com.example.quizapp.ui.CategorySelectionActivity;
import com.example.quizapp.ui.SynchronizationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tagg";

    private FirebaseDatabase database;
    private DatabaseReference categoriesReference;
    private DatabaseReference questionsReference;
    HashMap<String, HashMap<String, String>> categories;
    HashMap<String, HashMap<String, String>> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        categoriesReference = database.getReference("Categories");
        categoriesReference = database.getReference("Questions");

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CategorySelectionActivity.class));
            }
        });

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SynchronizationActivity.class));
            }
        });
    }
}
