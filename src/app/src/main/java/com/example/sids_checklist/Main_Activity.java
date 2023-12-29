package com.example.sids_checklist;

/*
Code created with reference to Mohit Singh's To Do List App Android Studio Tutorial

This is the main activity which allows the user to access the sleeping checklist,

*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistreports.Checklist_Reports;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main_Activity extends AppCompatActivity {
    private Profile_DatabaseHandler db;
    private List<ProfileModel> profileList;
    private List<String> usernameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Perform database setup
        db = new Profile_DatabaseHandler(this);
        db.openDatabase();

        profileList = new ArrayList<>();
        profileList = db.getAllProfiles();

        // This should be turned into an adapter or other simplified function in the future
        usernameList = new ArrayList<>();
        usernameList = db.getAllUsernames();

        // For the sake of testing, we will create two profiles:
//        ProfileModel profile = new ProfileModel();
//        profile.setUsername("Henry");
//        db.insertProfile(profile);
//        profile.setUsername("Mary");
//        db.insertProfile(profile);

        // Hide action bar so top most navigation is hidden
        Objects.requireNonNull(getSupportActionBar()).hide();


        Spinner profile_select = (Spinner) findViewById(R.id.profile_select);
        ArrayAdapter<String> usernameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usernameList);
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_select.setAdapter(usernameAdapter);

        Button goToChecklist = findViewById(R.id.goToChecklist);
        Button goToReport = findViewById(R.id.goToReport);
        // Button goToProfile = findViewById(R.id.goToProfile);
        /// Button goToSetup = findViewById(R.id.goToSetup);
        Button goToManageUsers = findViewById(R.id.goToManageUsers);

        profile_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUsername = parent.getItemAtPosition(position).toString();
                int selectedProfileID = db.getIDByUsername(selectedUsername);

                goToChecklist.setOnClickListener(v -> {
                    Intent i = new Intent(Main_Activity.this, Checklist_Activity.class);
                    i.putExtra("profile_id", selectedProfileID);
                    startActivity(i);
                });

                goToReport.setOnClickListener(v -> {
                    Intent i = new Intent(Main_Activity.this, Checklist_Reports.class);
                    i.putExtra("profile_id", selectedProfileID);
                    startActivity(i);
                });

                goToManageUsers.setOnClickListener(v -> {
                    Intent i = new Intent(Main_Activity.this, Profile_Activity.class);
                    startActivity(i);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                goToManageUsers.setOnClickListener(v -> {
                    Intent i = new Intent(Main_Activity.this, Profile_Activity.class);
                    startActivity(i);
                });
            }
        });
    }


}