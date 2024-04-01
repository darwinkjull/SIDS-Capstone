package com.example.sids_checklist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

/**
 * This class represents the settings activity where users can configure various app settings.
 */
public class Settings_Activity extends AppCompatActivity {

    /**
     * Initializes the settings activity when created.
     * It sets up UI components, hides the action bar, and initializes listeners for buttons and switches.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button back = findViewById(R.id.SettingsBack);
        Button pinReset = findViewById(R.id.buttonPinReset);
        Button questionReset = findViewById(R.id.buttonSecurityReset);
        @SuppressLint("UseSwitchCompatOrMaterialCode") SwitchCompat switchDarkView = findViewById(R.id.idDarkSwitch);
        @SuppressLint("UseSwitchCompatOrMaterialCode") SwitchCompat switchPinEnableView = findViewById(R.id.idEnablePinSwitch);
        switchPinEnableView.setChecked(getPinStatus());

        //TODO: add dark mode capability
        /*
        switchDarkView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        */
        switchPinEnableView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Callback method to be invoked when the checked state of a compound button (switch) changes.
             * It saves the PIN status (enabled/disabled) based on the switch state.
             *
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView, true if the button is checked, false otherwise.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePinDisabled(isChecked);
            }
        });

        back.setOnClickListener(v -> startActivity(new Intent(Settings_Activity.this,
                Main_Activity.class)));
        pinReset.setOnClickListener(v -> startActivity(new Intent(Settings_Activity.this,
                Pin_Setup_Settings.class)));
        questionReset.setOnClickListener(v -> startActivity(new Intent(Settings_Activity.this,
                Setup_Security_Questions_Settings.class)));
    }

    /**
     * Saves the PIN status (enabled/disabled) in SharedPreferences.
     *
     * @param pinStatus The status of the PIN (true if enabled, false if disabled).
     */
    private void savePinDisabled(Boolean pinStatus) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("pinStatus", pinStatus);
        editor.apply();
    }

    /**
     * Retrieves the PIN status (enabled/disabled) from SharedPreferences.
     *
     * @return The PIN status (true if enabled, false if disabled).
     */
    private Boolean getPinStatus() {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getBoolean("pinStatus", false);
    }
}
