package com.example.sids_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.checklistadapter.ProfileListAdapter;
import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.List;
import java.util.Objects;

public class Sharing_Activity extends AppCompatActivity {
    private int profileID;
    private List<ProfileModel> profileModelList;
    private ProfileListAdapter profileListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        profileID = getIntent().getIntExtra("profile_id", -1);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(this);
        profile_db.openDatabase();

        Button returnFromSharingButton = findViewById(R.id.returnFromSharingButton);
        Button sendInfoButton = findViewById(R.id.sendInfoButton);
//        Button receiveInfoButton = findViewById(R.id.receiveInfoButton);
        RecyclerView profileRecyclerList = findViewById(R.id.profilesSharingList);

        profileModelList = profile_db.getAllProfiles();
        profileListAdapter = new ProfileListAdapter(profile_db, this, profileModelList);
        profileRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        profileRecyclerList.setAdapter(profileListAdapter);

        returnFromSharingButton.setOnClickListener(v -> {
            Intent i = new Intent(Sharing_Activity.this, Main_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        sendInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sharing_SendInfo sendInfoPopup = new Sharing_SendInfo();
                sendInfoPopup.showAddProfilePopUp(v);
            }
        });
//        receiveInfoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Sharing_ReceiveInfo receiveInfoPopup = new Sharing_ReceiveInfo();
//                receiveInfoPopup.showAddProfilePopUp(v);
//            }
//        });
    }

}
