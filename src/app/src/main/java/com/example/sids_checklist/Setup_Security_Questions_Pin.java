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
 * This activity allows the user to set up security questions along with their answers for PIN recovery.
 */
public class Setup_Security_Questions_Pin extends AppCompatActivity {
    private EditText questionText;
    private EditText answerText;
    private Button saveButton;

    /**
     * Called when the activity is starting. Responsible for initializing UI components
     * and setting up event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains
     *                           the data it most recently supplied in onSaveInstanceState(Bundle).
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


        backButton.setOnClickListener(v -> startActivity(new Intent(Setup_Security_Questions_Pin.this, Pin_Setup_Pin.class)));

        answerText.addTextChangedListener(new TextWatcher() {
            /**
             * invoked before the text is changed
             *
             * @param s      The text before it has been changed.
             * @param start  The position of the beginning of the changed part in the text.
             * @param count  The length of the changed part in the text.
             * @param after  The length of the new text that replaces the changed part.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * This method is called when the text in the EditText is changed.
             * It is invoked after the text has been changed, to change the colour
             * of the save button displayed on screen, indicating to the user
             * that they have input a valid string.
             *
             * @param s      The modified text.
             * @param start  The starting position of the changed part in the text.
             * @param before The length of the text that has been replaced.
             * @param count  The length of the new text.
             */
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

            /**
             * invoked after the text is changed
             *
             * @param s      The text before it has been changed.
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
                startActivity(new Intent(Setup_Security_Questions_Pin.this, Main_Activity.class));
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
     * @param Answer The hashed answer to be saved.
     */
    private void saveAnswer(String Answer) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Answer", Answer);
        editor.apply();
    }

    /**
     * Hashes the provided password using SHA-512 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException Thrown if the SHA-512 algorithm is not available.
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

