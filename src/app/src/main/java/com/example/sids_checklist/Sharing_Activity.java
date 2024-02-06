package com.example.sids_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.Objects;

public class Sharing_Activity extends AppCompatActivity {
    private int profileID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        profileID = getIntent().getIntExtra("profile_id", -1);

        Profile_DatabaseHandler db = new Profile_DatabaseHandler(this);
        db.openDatabase();

        Button returnFromSharingButton = findViewById(R.id.returnFromSharingButton);
        Button sendInfoButton = findViewById(R.id.sendInfoButton);
        Button receiveInfoButton = findViewById(R.id.receiveInfoButton);

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
        receiveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sharing_ReceiveInfo receiveInfoPopup = new Sharing_ReceiveInfo();
                receiveInfoPopup.showAddProfilePopUp(v);
            }
        });
    }

}
