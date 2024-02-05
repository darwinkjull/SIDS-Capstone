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

public class Settings_Activity extends AppCompatActivity {
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


        /*switchDarkView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });*/
        switchPinEnableView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePinDisabled(isChecked);
            }
        });


        back.setOnClickListener(v -> startActivity(new Intent(Settings_Activity.this, Main_Activity.class)));
        pinReset.setOnClickListener(v -> startActivity(new Intent(Settings_Activity.this, Pin_Setup_Settings.class)));
        questionReset.setOnClickListener(v -> startActivity(new Intent(Settings_Activity.this, Setup_Security_Questions_Settings.class)));

    }

    private void savePinDisabled(Boolean pinStatus) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("pinStatus", pinStatus);
        editor.apply();
    }
    private Boolean getPinStatus() {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getBoolean("pinStatus", false);
    }
}
