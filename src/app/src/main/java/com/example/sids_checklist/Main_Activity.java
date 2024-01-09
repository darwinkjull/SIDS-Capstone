package com.example.sids_checklist;

/*
Code created with reference to Mohit Singh's To Do List App Android Studio Tutorial

This is the main activity which allows the user to access the sleeping checklist,

*/

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistreports.Checklist_Reports;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main_Activity extends AppCompatActivity {
    private Profile_DatabaseHandler db;
    private List<ProfileModel> profileList;
    private List<String> usernameList;

    private Checklist_DatabaseHandler dbChecklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Perform database setup
        Log.d("tag", "Creating profile DB");
        db = new Profile_DatabaseHandler(this);
        db.openDatabase();
        Log.d("tag", "Profile DB success");

        Log.d("tag", "Creating checklist DB");
        dbChecklist = new Checklist_DatabaseHandler(this);
        dbChecklist.openDatabase();
        Log.d("tag", "Checklist DB successful");


        // As of right now, there is no use for the full profile on the home page, just the username
        profileList = new ArrayList<>();
        profileList = db.getAllProfiles();

        // This could be turned into an adapter or other simplified function in the future
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

        // Set up spinner (drop down menu) to house the profiles we can select
        // The ArrayAdapter is used to put our list of usernames into the drop down menu
        Spinner profile_select = (Spinner) findViewById(R.id.profile_select);
        ArrayAdapter<String> usernameAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, usernameList);
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_select.setAdapter(usernameAdapter);

        // Defining the buttons on the home page
        Button goToChecklist = findViewById(R.id.goToChecklist);
        Button goToReport = findViewById(R.id.goToReport);
        // Button goToProfile = findViewById(R.id.goToProfile);
        // Button goToSetup = findViewById(R.id.goToSetup);
        Button goToManageUsers = findViewById(R.id.goToProfile);

        /* This itemSelectedListener will allow us to navigate using the buttons only when
        an item from the list has been chosen
         */
        profile_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUsername = parent.getItemAtPosition(position).toString();
                // Getting profile ID from the selected user name
                int selectedProfileID = db.getIDByUsername(selectedUsername);

                // Providing profile ID with the intents to activities in our application
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
                    i.putExtra("profile_id", selectedProfileID);
                    startActivity(i);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // If no option selected, assume we have a blank list, force user to go to profiles
                goToManageUsers.setOnClickListener(v -> {
                    Intent i = new Intent(Main_Activity.this, Profile_Activity.class);
                    startActivity(i);
                });
            }
        });
    }


}