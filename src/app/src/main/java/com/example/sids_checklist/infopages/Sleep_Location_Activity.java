package com.example.sids_checklist.infopages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.R;

import java.util.Objects;

public class Sleep_Location_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_location_layout);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button backButton = findViewById(R.id.InfoLocationBack);

        backButton.setOnClickListener(v-> startActivity(new Intent(Sleep_Location_Activity.this, Info_Page_Activity.class)));
    }
}
