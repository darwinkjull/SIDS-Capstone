package com.example.sids_checklist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.checklistadapter.ProfileListAdapter;
import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistprofiles.Profile_PopUpInterface;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;
import com.example.sids_checklist.checklistprofiles.Profile_DateHandler;

import java.util.List;
import java.util.Objects;

public class Profile_Activity extends AppCompatActivity implements Profile_PopUpInterface {

    private int profileID;
    private Context context;
    private Profile_DatabaseHandler profile_db;
    private List<ProfileModel> profileModelList;
    private ProfileListAdapter profileListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        context = this;

        profileID = getIntent().getIntExtra("profile_id", -1);

        profile_db = new Profile_DatabaseHandler(this);
        profile_db.openDatabase();

        checkForProfiles();

        Button addProfileButton = findViewById(R.id.addProfileButton);
        Button returnFromProfilesButton = findViewById(R.id.returnFromProfilesButton);

        returnFromProfilesButton.setOnClickListener(v -> {
            Intent i = new Intent(Profile_Activity.this, Main_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        Profile_AddProfile addProfilePopUp = new Profile_AddProfile();
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProfilePopUp.showAddProfilePopUp(v, context);
            }
        });

    }

    public int getProfileID() {
        assert (profileID != -1);
        return profileID;
    }

    private void updateProfileDisplay(){
        TextView selectedNameText = findViewById(R.id.profileName);
        TextView selectedAgeText = findViewById(R.id.profileAge);
        ImageView selectedImage = findViewById(R.id.profileImage);
        if (profileID != -1) {
            selectedNameText.setText(profile_db.getProfileInfoFromID(profileID).getUsername());
            Profile_DateHandler profile_date = new Profile_DateHandler(profile_db.getProfileInfoFromID(profileID));
            selectedAgeText.setText(profile_date.getWeeks());
            int colorID = Profile_Activity.this.getResources().getIdentifier(profile_db.getProfileInfoFromID(profileID).getProfile_color(), "color", Profile_Activity.this.getPackageName());
            if (colorID != 0){selectedImage.setColorFilter(ContextCompat.getColor(this, colorID), PorterDuff.Mode.SRC_IN);}
        }
    }

    private void checkForProfiles(){
        List<Integer> idList = profile_db.getAllID();
        ViewSwitcher profileViewSwitcher = findViewById(R.id.profileInfo_Switcher);
        if (!(idList.isEmpty())){
            // Check if the profileID we are using still exists
            if (!(idList.contains(profileID))){
                // If the ID no longer exists, fetch a new ID to use instead
                this.profileID = idList.get(0);
            }
            // PUT VIEW SELECTOR HERE - CHOOSE VIEW THAT SHOWS PROFILE INFO
            profileViewSwitcher.setDisplayedChild(profileViewSwitcher.indexOfChild(findViewById(R.id.profileInfo_profilesExist)));
            // Initialize elements of this view

            Button editProfileButton = findViewById(R.id.editProfileButton);
            Button deleteProfileButton = findViewById(R.id.deleteProfileButton);

            RecyclerView profileRecyclerList = findViewById(R.id.profilesList);

            editProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile_EditProfile editProfilePopUp = new Profile_EditProfile();
                    editProfilePopUp.showEditProfilePopUp(v, context, profileID);
                }
            });

            deleteProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile_DeleteProfile deleteProfilePopUp = new Profile_DeleteProfile();
                    deleteProfilePopUp.showDeleteProfilePopUp(v, context, profileID);
                }
            });

            profileModelList = profile_db.getAllProfiles();
            profileListAdapter = new ProfileListAdapter(profile_db, this, profileModelList);
            profileRecyclerList.setLayoutManager(new LinearLayoutManager(this));
            profileRecyclerList.setAdapter(profileListAdapter);

            updateProfileDisplay();
        }
        else {
            // PUT VIEW SELECTOR HERE - CHOOSE VIEW THAT SHOWS NO PROFILES EXIST
            profileViewSwitcher.setDisplayedChild(profileViewSwitcher.indexOfChild(findViewById(R.id.profileInfo_profilesNotExist)));
        }
    }

    @Override
    public void refreshProfiles(){
        checkForProfiles();
    }

    @Override
    public void refreshProfilesAdded(int newProfileID){
        this.profileID = newProfileID;
        checkForProfiles();
    }
}
