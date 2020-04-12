package com.example.quizapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.model.Category;
import com.example.quizapp.model.Question;

import java.lang.ref.WeakReference;
import java.util.List;

public class PlayingActivity extends AppCompatActivity {

    private static String INTENT_PARAM_CATEGORY = "category";

    private Category category;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    private Button[] answerButtons = new Button[4];

    public static Intent createIntentForQuestionActivity(Context context, Category category) {
        Intent intent = new Intent(context, PlayingActivity.class);
        intent.putExtra(INTENT_PARAM_CATEGORY, category);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        this.category = (Category) getIntent().getSerializableExtra(INTENT_PARAM_CATEGORY);

        this.answerButtons[0] = findViewById(R.id.playing_activity_button_answer_1);
        this.answerButtons[1] = findViewById(R.id.playing_activity_button_answer_2);
        this.answerButtons[2] = findViewById(R.id.playing_activity_button_answer_3);
        this.answerButtons[3] = findViewById(R.id.playing_activity_button_answer_4);

        for(int i = 0; i < answerButtons.length; i++) {
            final int answerNumber = i+1;
            answerButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(answerNumber == questions.get(currentQuestionIndex).correctAnswer) {
                        v.setBackground(getDrawable(R.drawable.gradient_green));
                        correctAnswers++;
                    } else {
                        v.setBackground(getDrawable(R.drawable.gradient_red));
                    }
                    setAnswerButtonsState(false);
                }
            });
        }

        Button nextButton = findViewById(R.id.playing_activity_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsState(true);
                currentQuestionIndex++;
                if(currentQuestionIndex < questions.size()) {
                    updateViews();
                } else {
                    //todo show result
                }
            }
        });

        new LoadQuestionsAsyncTask(this, category).execute();
    }

    private static class LoadQuestionsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<PlayingActivity> playingActivityWeakReference;
        private List<Question> queryResult;
        private Category category;

        LoadQuestionsAsyncTask(PlayingActivity context, Category category) {
            this.playingActivityWeakReference = new WeakReference<>(context);
            this.category = category;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PlayingActivity playingActivity = playingActivityWeakReference.get();
            if(playingActivity == null || playingActivity.isFinishing()) {
                return null;
            }

            queryResult = AppDatabase.getInstance(playingActivity).questionDao().getQuestionsByCategoryId(category.categoryId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            PlayingActivity playingActivity = playingActivityWeakReference.get();
            if(playingActivity == null || playingActivity.isFinishing()) {
                return;
            }

            playingActivity.questions = queryResult;
            playingActivity.setButtonsState(true);
            playingActivity.updateViews();
        }
    }

    private void updateViews() {
        if(questions == null || currentQuestionIndex >= questions.size()) {
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        String[] answers = new String[]{currentQuestion.answer_1, currentQuestion.answer_2, currentQuestion.answer_3, currentQuestion.answer_4};
        for(int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setBackground(getDrawable(R.drawable.gradient_primary));
            answerButtons[i].setText(answers[i]);
        }

        ((TextView)findViewById(R.id.playing_activity_text_view_question_content)).setText(questions.get(currentQuestionIndex).questionContent);
        ((TextView)findViewById(R.id.playing_activity_text_view_score_count)).setText(new StringBuilder().append(getString(R.string.playing_activity_text_view_score_count)).append(" ").
                append(currentQuestionIndex + 1).append("/").append(questions.size()).toString());
    }

    private void setAnswerButtonsState(boolean isEnable) {
        for(Button button : answerButtons) {
            button.setEnabled(isEnable);
        }
    }

    private void setButtonsState(boolean isEnable) {
        setAnswerButtonsState(isEnable);
        findViewById(R.id.playing_activity_button_next).setEnabled(isEnable);
    }
}
