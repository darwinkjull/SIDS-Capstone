package com.example.sids_checklist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.checklistadapter.ProfileListAdapter;
import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.List;
import java.util.Objects;

public class Profile_Activity extends AppCompatActivity {

    private int profileID;
    private Profile_DatabaseHandler profile_db;
    private List<ProfileModel> profileModelList;
    private ProfileListAdapter profileListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        profileID = getIntent().getIntExtra("profile_id", -1);

        profile_db = new Profile_DatabaseHandler(this);
        profile_db.openDatabase();

        TextView selectedNameText = findViewById(R.id.profileName);
        if (profileID != -1) {
            selectedNameText.setText(profile_db.getProfileInfoFromID(profileID).getUsername());
        }

        Button returnFromProfilesButton = findViewById(R.id.returnFromProfilesButton);
        Button editProfileButton = findViewById(R.id.editProfileButton);
        Button deleteProfileButton = findViewById(R.id.deleteProfileButton);
        Button addProfileButton = findViewById(R.id.addProfileButton);

        RecyclerView profileRecyclerList = findViewById(R.id.profilesList);



        returnFromProfilesButton.setOnClickListener(v -> {
            Intent i = new Intent(Profile_Activity.this, Main_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_AddProfile addProfilePopUp = new Profile_AddProfile();
                addProfilePopUp.showAddProfilePopUp(v);
            }
        });

        if (profileID != -1) {
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

            profileModelList = profile_db.getAllProfiles();
            profileListAdapter = new ProfileListAdapter(profile_db, this, profileModelList);
            profileRecyclerList.setLayoutManager(new LinearLayoutManager(this));
            profileRecyclerList.setAdapter(profileListAdapter);
        }

    }

    public int getProfileID() {
        assert (profileID != -1);
        return profileID;
    }
}
