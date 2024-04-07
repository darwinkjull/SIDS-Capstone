package com.example.sids_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.checklistadapter.SetUpAdapter;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;

import java.util.ArrayList;
import java.util.Objects;

/**
 *  This class is dedicated to displaying the recommended tips from the literature to the user
 *  so that they can add them to their own checklist.
 */
public class Checklist_Setup_Activity extends AppCompatActivity {

    private SetUpAdapter tipsAdapterSmoking;
    private SetUpAdapter tipsAdapterCoSleeping;
    private SetUpAdapter tipsAdapterStandard;
    private Checklist_DatabaseHandler db;
    private int profileID;

    /**
     * This method initializes the activity layout, hides the action bar,
     * retrieves the profile ID from the intent, opens the checklist database,
     * sets up RecyclerViews for each section, initializes items in the RecyclerViews,
     * sets up adapters, and implements click listeners for the add and back buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggested_checklist);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //get profile ID
        profileID = getIntent().getIntExtra("profile_id", -1);

        //open checklist database
        db = new Checklist_DatabaseHandler(this);
        db.openDatabase();
        //setup recycler view for each section
        RecyclerView tasksRecyclerViewSmoking = findViewById(R.id.SmokingRecycler);
        tasksRecyclerViewSmoking.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView tasksRecyclerViewCoSleeping = findViewById(R.id.CoSLeepingRecycler);
        tasksRecyclerViewCoSleeping.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView tasksRecyclerViewStandard = findViewById(R.id.StadardRecycler);
        tasksRecyclerViewStandard.setLayoutManager(new LinearLayoutManager(this));
        //initialize items in recycler view
        tipsAdapterSmoking = new SetUpAdapter();
        tipsAdapterCoSleeping = new SetUpAdapter();
        tipsAdapterStandard = new SetUpAdapter();

        tasksRecyclerViewSmoking.setAdapter(tipsAdapterSmoking);
        Tips smoking = new Tips();
        ArrayList<Tips> smokingTips = smoking.initSmokingTips();
        tasksRecyclerViewCoSleeping.setAdapter(tipsAdapterCoSleeping);
        Tips coSleeping = new Tips();
        ArrayList<Tips> coSleepingTips = coSleeping.initCoSleepingTips();
        tasksRecyclerViewStandard.setAdapter(tipsAdapterStandard);
        Tips standard = new Tips();
        ArrayList<Tips> standardTips = standard.initStandardTips();

        tipsAdapterSmoking.setTips(smokingTips);
        tipsAdapterCoSleeping.setTips(coSleepingTips);
        tipsAdapterStandard.setTips(standardTips);

        //implement the add button
        Button add = findViewById(R.id.suggestedAdd);
        add.setOnClickListener(v -> {
            checkCheckedItems(tipsAdapterSmoking);
            checkCheckedItems(tipsAdapterCoSleeping);
            checkCheckedItems(tipsAdapterStandard);
            Intent i = new Intent(Checklist_Setup_Activity.this, Checklist_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        Button back = findViewById(R.id.checklistButtonBack);
        back.setOnClickListener(v -> {
            Intent i = new Intent(Checklist_Setup_Activity.this, Checklist_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });
    }
    /**
     * Checks the checked items in the adapter and inserts them into the database.
     *
     * @param adapter The adapter containing the checklist items.
     */
    private void checkCheckedItems(SetUpAdapter adapter){
        for (int i = 0; i < adapter.getItemCount(); i++){
            Tips item = adapter.getItem(i);

            if (item.getStatus()==1){
                db.insertTip(item, profileID);
            }
        }
    }
    /**
     * Retrieves the profile ID associated with this activity.
     *
     * @return The profile ID.
     */
    public int getProfileID(){return profileID;}
}

