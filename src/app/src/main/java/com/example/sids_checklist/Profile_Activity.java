package com.example.sids_checklist;

import android.annotation.SuppressLint;
import android.content.Context;
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

/**
 * Profile_Activity displays and manages user profiles.
 * This activity allows users to view, add, edit, and delete profiles.
 */
public class Profile_Activity extends AppCompatActivity implements Profile_PopUpInterface {

    private int profileID;
    private Context context;
    private Profile_DatabaseHandler profile_db;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState Bundle containing the activity's previously saved state
     */
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

        Button addProfileButtonNoProfile = findViewById(R.id.addProfileButton_NoProfileSelected);
        Button returnFromProfilesButton = findViewById(R.id.returnFromProfilesButton);

        addProfileButtonNoProfile.setOnClickListener(new View.OnClickListener() {

            /**
             * create the add profile view
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Profile_AddProfile addProfilePopUp = new Profile_AddProfile();
                addProfilePopUp.showAddProfilePopUp(v, context);
            }
        });

        returnFromProfilesButton.setOnClickListener(v -> {
            Intent i = new Intent(Profile_Activity.this, Main_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });


    }

    /**
     * Retrieves the ID of the current profile.
     *
     * @return The ID of the current profile
     * @throws AssertionError If the profileID is -1 (indicating no profile is selected)
     */
    public int getProfileID() {
        assert (profileID != -1);
        return profileID;
    }

    /**
     * Updates the display with information about the selected profile.
     * Sets the profile name, age, and image color based on the selected profile's data.
     * If no profile is selected, the display remains unchanged.
     */
    private void updateProfileDisplay(){
        TextView selectedNameText = findViewById(R.id.profileName);
        TextView selectedAgeText = findViewById(R.id.profileAge);
        ImageView selectedImage = findViewById(R.id.profileImage);
        if (profileID != -1) {
            selectedNameText.setText(profile_db.getProfileInfoFromID(profileID).getUsername());
            Profile_DateHandler profile_date = new Profile_DateHandler(profile_db.getProfileInfoFromID(profileID));
            selectedAgeText.setText(profile_date.getWeeks());
            @SuppressLint("DiscouragedApi") int colorID = Profile_Activity.this.getResources().getIdentifier(profile_db.getProfileInfoFromID(profileID).getProfile_color(), "color", Profile_Activity.this.getPackageName());
            if (colorID != 0){selectedImage.setColorFilter(ContextCompat.getColor(this, colorID), PorterDuff.Mode.SRC_IN);}
        }
    }

    /**
     * Checks for the existence of profiles and updates the UI accordingly.
     * If profiles exist, it sets up the view to display profile information, including buttons for adding, editing, and deleting profiles.
     * If no profiles exist, it switches to a view indicating that no profiles are available.
     */
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


            Button addProfileButton = findViewById(R.id.addProfileButton);
            Button editProfileButton = findViewById(R.id.editProfileButton);
            Button deleteProfileButton = findViewById(R.id.deleteProfileButton);

            RecyclerView profileRecyclerList = findViewById(R.id.profilesList);

            addProfileButton.setOnClickListener(v -> {
                Profile_AddProfile addProfilePopUp = new Profile_AddProfile();
                addProfilePopUp.showAddProfilePopUp(v, context);
            });

            editProfileButton.setOnClickListener(v -> {
                Profile_EditProfile editProfilePopUp = new Profile_EditProfile();
                editProfilePopUp.showEditProfilePopUp(v, context, profileID);
            });

            deleteProfileButton.setOnClickListener(v -> {
                Profile_DeleteProfile deleteProfilePopUp = new Profile_DeleteProfile();
                deleteProfilePopUp.showDeleteProfilePopUp(v, context, profileID);
            });

            List<ProfileModel> profileModelList = profile_db.getAllProfiles();
            ProfileListAdapter profileListAdapter = new ProfileListAdapter(profile_db, this, profileModelList);
            profileRecyclerList.setLayoutManager(new LinearLayoutManager(this));
            profileRecyclerList.setAdapter(profileListAdapter);

            profileListAdapter.setOnClickListener(profile -> {
                profileID = profile.getId();
                updateProfileDisplay();
            });

            updateProfileDisplay();
        }
        else {
            // PUT VIEW SELECTOR HERE - CHOOSE VIEW THAT SHOWS NO PROFILES EXIST
            profileViewSwitcher.setDisplayedChild(profileViewSwitcher.indexOfChild(findViewById(R.id.profileInfo_profilesNotExist)));
        }
    }

    /**
     * Refreshes the profile list by calling the method to check for profiles.
     */
    @Override
    public void refreshProfiles(){
        checkForProfiles();
    }

    /**
     * Refreshes the profile list after a new profile is added.
     *
     * @param newProfileID The ID of the newly added profile.
     */
    @Override
    public void refreshProfilesAdded(int newProfileID){
        this.profileID = newProfileID;
        checkForProfiles();
    }
}
