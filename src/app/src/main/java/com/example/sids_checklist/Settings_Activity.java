package com.example.sids_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
        //Button darkMode =

        back.setOnClickListener(v->startActivity(new Intent(Settings_Activity.this, Main_Activity.class)));
        pinReset.setOnClickListener(v->startActivity(new Intent(Settings_Activity.this, Pin_Setup_Settings.class)));
        questionReset.setOnClickListener(v->startActivity(new Intent(Settings_Activity.this, Setup_Security_Questions_Settings.class)));

    }
}
