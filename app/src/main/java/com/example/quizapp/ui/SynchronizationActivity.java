package com.example.quizapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizapp.MainActivity;
import com.example.quizapp.R;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.database.CategoryDao;
import com.example.quizapp.database.QuestionDao;
import com.example.quizapp.model.Category;
import com.example.quizapp.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class SynchronizationActivity extends AppCompatActivity {

    private DatabaseReference categoriesReference;
    private DatabaseReference questionsReference;
    HashMap<String, HashMap<String, String>> categories;
    HashMap<String, HashMap<String, String>> questions;
    private boolean CanFinish = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_synchronization);

        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Finish();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        categoriesReference = database.getReference("Categories");
        questionsReference = database.getReference("Questions");

        DownloadData();
    }

    private void Finish() {
        if(CanFinish) {
            finish();
        }
    }

    private void DownloadData() {
        CanFinish = false;
        categoriesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ((TextView)SynchronizationActivity.this.findViewById(R.id.txtInfo)).setText(R.string.categories_downloaded_info);
                categories = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();

                questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ((TextView)SynchronizationActivity.this.findViewById(R.id.txtInfo)).setText(R.string.questions_downloaded_info);
                        questions = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                        new SaveAsyncTask(SynchronizationActivity.this).execute();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        CanFinish = true;
                        ((TextView)SynchronizationActivity.this.findViewById(R.id.txtInfo)).setText(R.string.questions_downloading_error);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                CanFinish = true;
                ((TextView)SynchronizationActivity.this.findViewById(R.id.txtInfo)).setText(R.string.categories_downloading_error);
            }
        });
    }

    private static class SaveAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<SynchronizationActivity> synchronizationActivityWeakReference;

        public SaveAsyncTask(SynchronizationActivity synchronizationActivity) {
            synchronizationActivityWeakReference = new WeakReference<>(synchronizationActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SynchronizationActivity synchronizationActivity = synchronizationActivityWeakReference.get();
            if(synchronizationActivity == null || synchronizationActivity.isFinishing()) {
                return null;
            }

            CategoryDao categoryDao = AppDatabase.getInstance(synchronizationActivity).categoryDao();
            QuestionDao questionDao = AppDatabase.getInstance(synchronizationActivity).questionDao();

            categoryDao.deleteAllCategories();
            questionDao.deleteQuestions();

            if(synchronizationActivity.categories == null) {
                return null;
            }

            for(String categoryId : synchronizationActivity.categories.keySet()) {
                categoryDao.insertCategories(new Category(categoryId, synchronizationActivity.categories.get(categoryId).get("Name"), synchronizationActivity.categories.get(categoryId).get("Description")));
            }

            if(synchronizationActivity.questions == null) {
                return null;
            }

            for(String questionId : synchronizationActivity.questions.keySet()) {
                String correctAnswer = synchronizationActivity.questions.get(questionId).get("TrueAnswer");
                questionDao.insertQuestions(new Question(questionId, synchronizationActivity.questions.get(questionId).get("CategoryId"), synchronizationActivity.questions.get(questionId).get("Content"),
                        synchronizationActivity.questions.get(questionId).get("Answer1"), synchronizationActivity.questions.get(questionId).get("Answer2"),
                        synchronizationActivity.questions.get(questionId).get("Answer3"), synchronizationActivity.questions.get(questionId).get("Answer4"),
                        correctAnswer.charAt(correctAnswer.length()-1)-48));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SynchronizationActivity synchronizationActivity = synchronizationActivityWeakReference.get();
            if(synchronizationActivity == null || synchronizationActivity.isFinishing()) {
                return;
            }

            ((TextView)synchronizationActivity.findViewById(R.id.txtInfo)).setText(R.string.updated_successfully);
            ((ProgressBar)synchronizationActivity.findViewById(R.id.progressBar)).setVisibility(View.GONE);
            synchronizationActivity.CanFinish = true;
        }
    }

    @Override
    public void onBackPressed() {
        Finish();
    }
}
