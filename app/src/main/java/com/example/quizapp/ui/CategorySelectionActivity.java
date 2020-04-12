package com.example.quizapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.quizapp.R;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.model.Category;
import com.example.quizapp.ui.adapters.CategoryAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

public class CategorySelectionActivity extends AppCompatActivity implements CategorySelectionListener {

    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        new LoadCategoriesAsyncTask(this).execute();
    }

    protected void onCategoriesLoaded(final List<Category> categories) {
        this.categories = categories;

        RecyclerView recyclerView = findViewById(R.id.category_selection_activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CategoryAdapter(this.categories, this));
    }

    @Override
    public void onCategorySelect(final Category selectedCategory) {
        startActivity(PlayingActivity.createIntentForQuestionActivity(this, selectedCategory));
    }

    private static class LoadCategoriesAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<CategorySelectionActivity> categorySelectionActivityWeakReference;
        private List<Category> loadedCategories;

        public LoadCategoriesAsyncTask(final CategorySelectionActivity context) {
            categorySelectionActivityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CategorySelectionActivity categorySelectionActivity = categorySelectionActivityWeakReference.get();
            if(categorySelectionActivity == null || categorySelectionActivity.isFinishing()) {
                return null;
            }

            loadedCategories = AppDatabase.getInstance(categorySelectionActivity).categoryDao().getAllCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            CategorySelectionActivity categorySelectionActivity = categorySelectionActivityWeakReference.get();
            if(categorySelectionActivity == null || categorySelectionActivity.isFinishing()) {
                return;
            }

            categorySelectionActivity.onCategoriesLoaded(loadedCategories);
        }
    }
}
