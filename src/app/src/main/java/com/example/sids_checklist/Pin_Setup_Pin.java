package com.example.sids_checklist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that handles the setting of the user's pin
 *
 * Code created referencing Michey Faisal Integrate Android PassCode Security in your android
 * app Android Studio latest
 */
public class Pin_Setup_Pin extends AppCompatActivity implements View.OnClickListener{

    View view_bubble_01, view_bubble_02,view_bubble_03,view_bubble_04,view_bubble_05,view_bubble_06;
    Button btn_01,btn_02,btn_03,btn_04,btn_05,btn_06,btn_07,btn_08,btn_09,btn_00,btn_clear;

    ArrayList<String> num_list = new ArrayList<>();
    String pinCode;
    String num_01, num_02, num_03, num_04, num_05, num_06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.pin_setup);
        initializeComponents();
    }


    /**
     * Initializes the components of the PIN setup activity.
     * This method sets up the UI components such as buttons and view bubbles, and their event listeners.
     */
    private void initializeComponents() {
        view_bubble_01 = findViewById(R.id.view_bubble_01);
        view_bubble_02 = findViewById(R.id.view_bubble_02);
        view_bubble_03 = findViewById(R.id.view_bubble_03);
        view_bubble_04 = findViewById(R.id.view_bubble_04);
        view_bubble_05 = findViewById(R.id.view_bubble_05);
        view_bubble_06 = findViewById(R.id.view_bubble_06);

        btn_00 = findViewById(R.id.btn_00);
        btn_01 = findViewById(R.id.btn_01);
        btn_02 = findViewById(R.id.btn_02);
        btn_03 = findViewById(R.id.btn_03);
        btn_04 = findViewById(R.id.btn_04);
        btn_05 = findViewById(R.id.btn_05);
        btn_06 = findViewById(R.id.btn_06);
        btn_07 = findViewById(R.id.btn_07);
        btn_08 = findViewById(R.id.btn_08);
        btn_09 = findViewById(R.id.btn_09);
        btn_clear = findViewById(R.id.btn_Clear);

        btn_00.setOnClickListener(this);
        btn_01.setOnClickListener(this);
        btn_02.setOnClickListener(this);
        btn_03.setOnClickListener(this);
        btn_04.setOnClickListener(this);
        btn_05.setOnClickListener(this);
        btn_06.setOnClickListener(this);
        btn_07.setOnClickListener(this);
        btn_08.setOnClickListener(this);
        btn_09.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    /**
     * This method is invoked when any of the buttons in the PIN keypad are clicked.
     * It handles adding the corresponding number to the PIN input list and calling the passNumber method.
     *
     * @param view The view that was clicked, representing the button in the PIN keypad.
     */
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_00) {
            num_list.add("0");
        } else if (viewId == R.id.btn_01) {
            num_list.add("1");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_02) {
            num_list.add("2");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_03) {
            num_list.add("3");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_04) {
            num_list.add("4");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_05) {
            num_list.add("5");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_06) {
            num_list.add("6");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_07) {
            num_list.add("7");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_08) {
            num_list.add("8");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_09) {
            num_list.add("9");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if (viewId == R.id.btn_clear) {
            num_list.clear();
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Updates the visual representation of the PIN input bubbles based on the current PIN input list.
     *
     * @throws NoSuchAlgorithmException If the hashing algorithm used to hash the PIN code is not available.
     */
    private void passNumber() throws NoSuchAlgorithmException {
        if (num_list.size() == 0){
            view_bubble_01.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_02.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_03.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_04.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
        } else {
            if (num_list.size() == 1){
                num_01 = num_list.get(0);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            } else if (num_list.size() == 2) {
                num_02 = num_list.get(1);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            }else if (num_list.size() == 3) {
                num_03 = num_list.get(2);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            }else if (num_list.size() == 4) {
                num_04 = num_list.get(3);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            }else if (num_list.size() == 5) {
                num_05 = num_list.get(4);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            }else if (num_list.size() == 6) {
                num_06 = num_list.get(5);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                pinCode = num_01 + num_02 + num_03 + num_04 + num_05 + num_06;
                String hashedPin = hashPassword(pinCode);
                savePinCode(hashedPin);
            }
        }
    }

    /**
     * Saves the provided PIN code to the SharedPreferences.
     *
     * @param pinCode The PIN code to be saved.
     */
    private void savePinCode(String pinCode) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pincode", pinCode);
        editor.apply();
        startActivity(new Intent(Pin_Setup_Pin.this,Setup_Security_Questions_Pin.class));
    }

    /**
     * Hashes the provided password using the SHA-512 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the specified algorithm (SHA-512) is not available.
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

