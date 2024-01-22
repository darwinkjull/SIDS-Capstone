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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Setup_Security_Questions_Pin extends AppCompatActivity {
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


        backButton.setOnClickListener(v -> startActivity(new Intent(Setup_Security_Questions_Pin.this, Pin_Setup_Pin.class)));

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
                    saveButton.setBackgroundColor(ContextCompat.getColor(Setup_Security_Questions_Pin.this,
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
                try {
                    saveAnswer(hashPassword(answerText.getText().toString()));
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                startActivity(new Intent(Setup_Security_Questions_Pin.this, Main_Activity.class));
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

