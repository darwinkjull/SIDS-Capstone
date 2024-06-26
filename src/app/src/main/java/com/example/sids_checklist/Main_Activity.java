package com.example.sids_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.checklistreports.Checklist_Reports;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;
import com.example.sids_checklist.infopages.Info_Page_Activity;

import java.util.List;
import java.util.Objects;

/**
 * Class that encompasses the functionality of the Home Page for the application
 * and allows users to navigate to all other pages of the application
 */
public class Main_Activity extends AppCompatActivity {
    private Profile_DatabaseHandler profile_db;

    /**
     * This method initializes the main activity layout, hides the action bar,
     * sets up database access, populates the user profile spinner, and defines button click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Objects.requireNonNull(getSupportActionBar()).hide();

        //Fetch information, if we were passed it returning from the previous acitivity
        int returnProfileID = getIntent().getIntExtra("profile_id", -1);

        //Perform database setup
        profile_db = new Profile_DatabaseHandler(this);
        profile_db.openDatabase();

        // This could be turned into an adapter or other simplified function in the future
        List<String> usernameList;
        usernameList = profile_db.getAllUsernames();

        // Set up spinner (drop down menu) to house the profiles we can select
        // The ArrayAdapter is used to put our list of usernames into the drop down menu
        Spinner profile_select = findViewById(R.id.profile_select);
        ArrayAdapter<String> usernameAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, usernameList);
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_select.setAdapter(usernameAdapter);
        if (returnProfileID != -1) {
            profile_select.setSelection(usernameAdapter.getPosition(profile_db.getUsernameByID(returnProfileID)));
        }

        // Defining the buttons on the home page
        Button goToChecklist = findViewById(R.id.goToChecklist);
        Button goToReport = findViewById(R.id.goToReport);
        Button goToManageUsers = findViewById(R.id.goToProfile);
        Button goToInfo = findViewById(R.id.goToInfo);
        Button goToSharing = findViewById(R.id.goToSharing);
        Button goToSettings = findViewById(R.id.goToSettings);

        // If no option selected, assume we have a blank list, force user to go to profiles
        goToManageUsers.setOnClickListener(v -> {
            Intent i = new Intent(Main_Activity.this, Profile_Activity.class);
            startActivity(i);
        });
        goToInfo.setOnClickListener(v -> {
            Intent i = new Intent(Main_Activity.this, Info_Page_Activity.class);
            startActivity(i);});

        goToSettings.setOnClickListener(v -> {
            Intent i = new Intent(Main_Activity.this, Settings_Activity.class);
            startActivity(i);});


        /* This itemSelectedListener will allow us to navigate using the buttons only when
        an item from the list has been chosen
         */
        profile_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * This method retrieves the selected username from the spinner,
             * obtains the corresponding profile ID from the database,
             * and sets up intents for various activities in the application based on the selected profile.
             *
             * @param parent   The AdapterView where the selection happened.
             * @param view     The view within the AdapterView that was clicked.
             * @param position The position of the view in the adapter.
             * @param id       The row id of the item that is selected.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUsername = parent.getItemAtPosition(position).toString();
                // Getting profile ID from the selected user name
                int selectedProfileID = profile_db.getIDByUsername(selectedUsername);

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

                goToSharing.setOnClickListener(v -> {
                    Intent i = new Intent(Main_Activity.this, Sharing_Activity.class);
                    i.putExtra("profile_id", selectedProfileID);
                    startActivity(i);
                });
            }

            /**
             * This method is invoked when the selection disappears from this view.
             *
             * @param parent The AdapterView where the selection was supposed to happen.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}