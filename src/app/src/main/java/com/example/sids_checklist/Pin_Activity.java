package com.example.sids_checklist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for that handles the activity of the Pin page
 *
 * Code created referencing Michey Faisal Integrate Android PassCode Security in your android app
 * Android Studio latest
 */
public class Pin_Activity extends AppCompatActivity implements View.OnClickListener {

    View view_bubble_01, view_bubble_02, view_bubble_03, view_bubble_04, view_bubble_05, view_bubble_06;
    Button btn_01, btn_02, btn_03, btn_04, btn_05, btn_06, btn_07, btn_08, btn_09, btn_reset, btn_00, btn_clear;

    ArrayList<String> num_list = new ArrayList<>();
    String pinCode;
    String num_01, num_02, num_03, num_04, num_05, num_06;

    /**
     * This method initializes the PIN activity layout, hides the action bar,
     * and determines the appropriate action to take based on the PIN setup status.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_activitymain);

        Objects.requireNonNull(getSupportActionBar()).hide();
        if (getPinCode().equals("") && getPinStatus().equals(false)) {
            startActivity(new Intent(Pin_Activity.this, Pin_Setup_Pin.class));

        } else if (!getPinCode().equals("") && getPinStatus().equals(true)) {
            startActivity(new Intent(Pin_Activity.this, Main_Activity.class));
        } else {
            initializeComponents();
        }
    }

    /**
     * Initializes the components of the PIN activity.
     * This method finds and assigns views and click listeners for buttons in the PIN activity layout.
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
        btn_reset = findViewById(R.id.btn_reset);
        btn_clear = findViewById(R.id.btn_clear);

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
        btn_reset.setOnClickListener(this);
    }

    /**
     * This method adds the corresponding number to the PIN input list when a number button is clicked,
     * clears the input list when the clear button is clicked,
     * and navigates to the security questions activity when the reset button is clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_00) {
            num_list.add("0");
            try {
                passNumber();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
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
        } else if (viewId == R.id.btn_reset) {
            startActivity(new Intent(Pin_Activity.this, Security_Questions_Ask.class));
        }
    }

    /**
     * This method updates the visual representation of the PIN input by changing the backgrounds
     * of the bubble views. If all digits are entered, it combines them into a PIN code,
     * hashes the PIN, and verifies it.
     *
     * @throws NoSuchAlgorithmException If the specified cryptographic algorithm is not available.
     */
    private void passNumber() throws NoSuchAlgorithmException {
        if (num_list.size() == 0) {
            view_bubble_01.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_02.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_03.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_04.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
            view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
        } else {
            if (num_list.size() == 1) {
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

            } else if (num_list.size() == 3) {
                num_03 = num_list.get(2);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            } else if (num_list.size() == 4) {
                num_04 = num_list.get(3);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            } else if (num_list.size() == 5) {
                num_05 = num_list.get(4);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_empty_oval_pin);

            } else if (num_list.size() == 6) {
                num_06 = num_list.get(5);
                view_bubble_01.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_02.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_03.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_04.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_05.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                view_bubble_06.setBackgroundResource(R.drawable.bg_view_solid_oval_pin);
                pinCode = num_01 + num_02 + num_03 + num_04 + num_05 + num_06;
                String hashedPin = Pin_Setup_Pin.hashPassword(pinCode);
                matchPinCode(hashedPin);
            }
        }


    }

    /**
     * Matches the entered PIN code with the stored PIN code.
     * If the entered PIN matches the stored PIN, navigates to the main activity.
     * If the entered PIN does not match the stored PIN, displays a toast indicating incorrect PIN
     * and clears the entered PIN digits.
     *
     * @param pinCode The entered PIN code to be matched.
     * @throws NoSuchAlgorithmException If the specified cryptographic algorithm is not available.
     */
    private void matchPinCode(String pinCode) throws NoSuchAlgorithmException {
        if (getPinCode().equals(pinCode)) {
            startActivity(new Intent(Pin_Activity.this, Main_Activity.class));
        } else {
            Toast.makeText(this, "Pin Incorrect", Toast.LENGTH_SHORT).show();
            num_list.clear();
            passNumber();
        }
    }

    /**
     * Retrieves the stored PIN code from SharedPreferences.
     *
     * @return The stored PIN code, or an empty string if the PIN code is not found.
     */
    private String getPinCode() {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getString("pincode", "");
    }

    /**
     * Retrieves the status of the PIN from SharedPreferences.
     *
     * @return The status of the PIN, true if set, false otherwise.
     */
    private Boolean getPinStatus() {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getBoolean("pinStatus", false);

    }
}
