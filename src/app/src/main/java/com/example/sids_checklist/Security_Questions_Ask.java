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
import java.util.Objects;

/**
 * This class represents the activity for asking security questions.
 * It allows the user to answer a security question and validates the answer.
 */
public class Security_Questions_Ask extends AppCompatActivity {
    private EditText answerText;
    private Button enterButton;

    /**
     * Initializes the activity when created.
     * It sets up the UI components, hides the action bar, and initializes listeners.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.security_question_ask);
        TextView questionText = findViewById(R.id.Question);
        answerText = findViewById(R.id.Answer);
        enterButton = findViewById(R.id.Enter);
        Button backButton = findViewById(R.id.QuestionBack);
        enterButton.setEnabled(false);

        questionText.setText(getQuestion());

        backButton.setOnClickListener(v -> startActivity(new Intent(Security_Questions_Ask.this, Pin_Activity.class)));

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
                    enterButton.setEnabled(false);
                    enterButton.setTextColor(Color.GRAY);
                } else {
                    enterButton.setEnabled(true);
                    enterButton.setBackgroundColor(ContextCompat.getColor(Security_Questions_Ask.this,
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

    /**
     * Validates the entered answer against the stored hash of the correct answer.
     * If the answer is correct, it starts the PIN setup activity; otherwise, it displays an error message.
     *
     * @throws NoSuchAlgorithmException If the hashing algorithm is not available.
     */
    private void matchAnswer() throws NoSuchAlgorithmException {
        if (getAnswer().equals(hashPassword(answerText.getText().toString()))) {
            startActivity(new Intent(Security_Questions_Ask.this, Pin_Setup_Pin.class));
        } else {
            Toast.makeText(this, "Answer Incorrect", Toast.LENGTH_SHORT).show();
            answerText.getText().clear();
        }
    }

    /**
     * Retrieves the correct answer hash from SharedPreferences.
     *
     * @return The hash of the correct answer.
     */
    private String getAnswer() {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getString("Answer", "");
    }

    /**
     * Retrieves the security question from SharedPreferences.
     *
     * @return The security question.
     */
    private String getQuestion() {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getString("Question", "");
    }

    /**
     * Computes the SHA-512 hash of the given password.
     *
     * @param password The password to be hashed.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException If the hashing algorithm is not available.
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
