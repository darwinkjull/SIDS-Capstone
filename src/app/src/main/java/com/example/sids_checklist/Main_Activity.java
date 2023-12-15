package com.example.sids_checklist;

/*
Code created with reference to Mohit Singh's To Do List App Android Studio Tutorial

This is the main activity which allows the user to access the sleeping checklist,

*/

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sids_checklist.checklistreports.Checklist_Reports;
import java.util.Objects;

public class Main_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Hide action bar so top most navigation is hidden
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button goToChecklist = findViewById(R.id.goToChecklist);
        Button goToReport = findViewById(R.id.goToReport);
        // Button goToProfile = findViewById(R.id.goToProfile);
        /// Button goToSetup = findViewById(R.id.goToSetup);
        Button goToManageUsers = findViewById(R.id.goToManageUsers);

        goToChecklist.setOnClickListener(v -> {
            Intent i = new Intent(Main_Activity.this, Checklist_Activity.class);
            startActivity(i);
        });

        goToReport.setOnClickListener(v -> {
            Intent i = new Intent(Main_Activity.this, Checklist_Reports.class);
            startActivity(i);
        });

        goToManageUsers.setOnClickListener(v -> {
            Intent i = new Intent(Main_Activity.this, Profile_Activity.class);
            startActivity(i);
        });
    }
}