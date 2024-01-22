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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security_Questions_Ask extends AppCompatActivity {
        private TextView questionText;
        private EditText answerText;
        private Button enterButton;
        private Button backButton;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.security_question_ask);
            questionText = findViewById(R.id.Question);
            answerText = findViewById(R.id.Answer);
            enterButton = findViewById(R.id.Enter);
            backButton = findViewById(R.id.QuestionBack);
            enterButton.setEnabled(false);

            questionText.setText(getQuestion());

            backButton.setOnClickListener(v -> startActivity(new Intent(Security_Questions_Ask.this, Pin_Activity.class)));

            answerText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() == 0) {
                        enterButton.setEnabled(false);
                        enterButton.setTextColor(Color.GRAY);
                    } else {
                        enterButton.setEnabled(true);
                        enterButton.setBackgroundColor(ContextCompat.getColor(Security_Questions_Ask.this,
                                R.color.colorPrimary));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ;
            enterButton.setOnClickListener(v -> {
                if (answerText.getText() == null) {
                    Toast.makeText(this, "Missing Answer", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        matchAnswer();
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        private void matchAnswer() throws NoSuchAlgorithmException {
            if (getAnswer().equals(hashPassword(answerText.getText().toString()))) {
                startActivity(new Intent(Security_Questions_Ask.this, Pin_Setup_Pin.class));
            } else {
                Toast.makeText(this, "Answer Incorrect", Toast.LENGTH_SHORT).show();
                answerText.getText().clear();
            }
        }

        private String getAnswer() {
            SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
            return preferences.getString("Answer", "");
        }

        private String getQuestion() {
            SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
            return preferences.getString("Question", "");


        }
    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for(byte b : mdArray) {
            int v = b & 0xff;
            if(v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();

    }
    }
