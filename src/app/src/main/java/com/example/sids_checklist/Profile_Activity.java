package com.example.sids_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sids_checklist.checklistreports.Checklist_Reports;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.Objects;

public class Profile_Activity extends AppCompatActivity {

    private int profileID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        profileID = getIntent().getIntExtra("profile_id", -1);
        assert (profileID != -1);

        Profile_DatabaseHandler db = new Profile_DatabaseHandler(this);
        db.openDatabase();

        TextView selectedNameText = findViewById(R.id.profileName);
        selectedNameText.setText(db.getProfileInfo(profileID).getUsername());

        Button returnFromProfileButton = findViewById(R.id.returnFromProfileButton);
        Button editProfileButton = findViewById(R.id.editProfileButton);
        Button deleteProfileButton = findViewById(R.id.deleteProfileButton);
        Button addProfileButton = findViewById(R.id.addProfileButton);



        returnFromProfileButton.setOnClickListener(v -> startActivity(new Intent(Profile_Activity.this,
                Main_Activity.class)));

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_AddProfile addProfilePopUp = new Profile_AddProfile();
                addProfilePopUp.showAddProfilePopUp(v);
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_EditProfile editProfilePopUp = new Profile_EditProfile();
                editProfilePopUp.showEditProfilePopUp(v, profileID);
            }
        });

        deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_DeleteProfile deleteProfilePopUp = new Profile_DeleteProfile();
                deleteProfilePopUp.showDeleteProfilePopUp(v, profileID);
            }
        });

    }
    public int getProfileID() {
        return profileID;
    }
}
