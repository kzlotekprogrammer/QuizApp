package com.example.quizapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.model.Result;

public class ResultActivity extends AppCompatActivity {

    private static final String INTENT_PARAM_RESULT = "result";

    private Result result;
    private Button return_menu;


    public static Intent createIntentForResultActivity(Context context, Result result) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(INTENT_PARAM_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = (Result) getIntent().getSerializableExtra(INTENT_PARAM_RESULT);

        ((TextView)findViewById(R.id.result_activity_score)).setText(result.getGoodAnswers() + "/" + result.getTotalQuestions());

        return_menu = findViewById(R.id.result_activity_return);
        return_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
