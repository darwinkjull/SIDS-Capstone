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
import java.util.Objects;

/**
 * Setup_Security_Questions_Settings class is responsible for managing the setup of security questions and answers.
 * Users can set up a security question along with its answer. The question and answer are stored securely.
 */
public class Setup_Security_Questions_Settings extends AppCompatActivity {
    private EditText questionText;
    private EditText answerText;
    private Button saveButton;

    /**
     * Initializes the activity when created.
     * Hides the action bar, sets the layout, and initializes UI elements.
     * Handles button clicks for saving security question and answer, as well as navigation back to the previous screen.
     * Enables/disables the save button based on answer input.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.security_question_setup);
        questionText = findViewById(R.id.QuestionSetUp);
        answerText = findViewById(R.id.AnswerSetUp);
        saveButton = findViewById(R.id.Save);
        Button backButton = findViewById(R.id.QuestionBack);
        saveButton.setEnabled(false);


        backButton.setOnClickListener(v-> startActivity(new Intent(Setup_Security_Questions_Settings.this, Pin_Setup_Settings.class)));

        answerText.addTextChangedListener(new TextWatcher() {

            /**
             * textwatcher for data before any text changes (not used)
             *
             * @param s character sequence
             * @param start the starting character position
             * @param count the number of characters
             * @param after the number of characters after changing
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * textwatcher module which will enable the save button when text changes
             *
             * @param s the character sequence
             * @param start the start character index
             * @param before the number of characters before change
             * @param count the total count of characters
             */
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

            /**
             * textwatcher module for after text changes (not used)
             * @param s the characher sequence
             */
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
                startActivity(new Intent(Setup_Security_Questions_Settings.this, Main_Activity.class));
            }
        });
    }

    /**
     * Saves the security question in SharedPreferences.
     *
     * @param Question The security question to be saved.
     */
    private void saveQuestion(String Question) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Question", Question);
        editor.apply();
    }

    /**
     * Saves the hashed answer in SharedPreferences.
     *
     * @param Answer The answer to be hashed and saved.
     */
    private void saveAnswer(String Answer) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Answer", Answer);
        editor.apply();
    }

    /**
     * Hashes the given password using SHA-512 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException if SHA-512 algorithm is not available.
     */
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