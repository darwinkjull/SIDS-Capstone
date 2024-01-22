package com.example.sids_checklist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Setup_Security_Questions_Settings extends AppCompatActivity {
    private EditText questionText;
    private EditText answerText;
    private Button saveButton;
    private Button backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_question_setup);
        questionText = findViewById(R.id.QuestionSetUp);
        answerText = findViewById(R.id.AnswerSetUp);
        saveButton = findViewById(R.id.Save);
        backButton = findViewById(R.id.QuestionBack);
        saveButton.setEnabled(false);


        backButton.setOnClickListener(v-> startActivity(new Intent(Setup_Security_Questions_Settings.this, Pin_Setup_Settings.class)));

        answerText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    saveButton.setEnabled(false);
                    saveButton.setTextColor(Color.GRAY);
                } else {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(ContextCompat.getColor(Setup_Security_Questions_Settings.this,
                            R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        saveButton.setOnClickListener(v -> {
            if (questionText.getText() == null) {
                Toast.makeText(this, "Missing Question", Toast.LENGTH_SHORT).show();

            } else {
                saveQuestion(questionText.getText().toString());
                saveAnswer(answerText.getText().toString());
                startActivity(new Intent(Setup_Security_Questions_Settings.this, Main_Activity.class));
            }
        });
    }

    private SharedPreferences.Editor saveQuestion(String Question) {
        SharedPreferences preferences = getSharedPreferences("Question pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Question", Question);
        editor.commit();
        return editor;
    }

    private SharedPreferences.Editor saveAnswer(String Answer) {
        SharedPreferences preferences = getSharedPreferences("Answer pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Answer", Answer);
        editor.commit();
        return editor;
    }
}