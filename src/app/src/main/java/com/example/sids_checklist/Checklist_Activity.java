package com.example.sids_checklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sids_checklist.checklistadapter.ChecklistAdapter;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for the activity of the Checklist and related
 * diaglogs. It uses a profileID passed from the Home Page activity to load
 * the  list of the user.
 *
 * Within this program, the adapters for the checklist databases will help
 * the user edit and save their items accordingly. This page also links to the
 * "Tips" page.
 *
 * The Checklist was inspired by Mohit Singh's To Do List App Android Studio Tutorial
 */
public class Checklist_Activity extends AppCompatActivity implements DialogCloseListener {
    private int profileID;
    private ChecklistAdapter checklistAdapter;
    private List<ChecklistModel> checklistList;
    private Checklist_DatabaseHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);

        // Hide action bar so top most navigation is hidden
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Get the profile ID that was passed into the activity using the intent
        profileID = getIntent().getIntExtra("profile_id", -1);
        assert (profileID != -1);

        // Open profile database so we can get profile info
        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(this);
        profile_db.openDatabase();

        // Open checklist database so we can modify checklist items
        db = new Checklist_DatabaseHandler(this);
        db.openDatabase();

        //Open checklist_util database so we can log the status of checklist items
        Checklist_UtilDatabaseHandler disp_db = new Checklist_UtilDatabaseHandler(this);
        disp_db.openDatabase();


        // On startup, initialize new empty array of checklist items
        checklistList = new ArrayList<>();

        // reference recyclerview item in checklist_activitymain.xml
        // Initialize variables to be used by RecylcerView adaptive list API
        RecyclerView checklistRecyclerView = findViewById(R.id.checklistRecyclerView);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        checklistAdapter = new ChecklistAdapter(db, disp_db, this);
        checklistRecyclerView.setAdapter(checklistAdapter);

        // Add the "ADD" "button capability onto screen
        Button fab = findViewById(R.id.checklistFAB);

        // Add the "ADD" "button capability onto screen
        Button save = findViewById(R.id.checklistConfirm);

        // Add button to return to main activity
        Button returnFromChecklistButton = findViewById(R.id.returnFromChecklistButton);

        Button tips = findViewById(R.id.buttonTips);

        // add item creator helper to reference in main using recyclerview api
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new Checklist_RecyclerItemTouchHelper(checklistAdapter));
        itemTouchHelper.attachToRecyclerView(checklistRecyclerView);

        // display current items in the database (newest first)
        checklistList = db.getAllItems(profileID);
        Collections.reverse(checklistList);
        // checklistAdapter.refreshItems(checklistList);
        checklistAdapter.setItems(checklistList);

        // listen for "ADD" button being pressed by user
        // if pressed, continue to Item adding functionality in Checklist_AddNewItem
        fab.setOnClickListener(
                v -> Checklist_AddNewItem.newInstance().show(getSupportFragmentManager(),
                        Checklist_AddNewItem.TAG));

        returnFromChecklistButton.setOnClickListener(v -> {
            Intent i = new Intent(Checklist_Activity.this, Main_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        save.setOnClickListener(
                v -> {
                    checklistAdapter.refreshItems(checklistList);
                    swipeRefreshLayout.setRefreshing(false);
                }
        );
        tips.setOnClickListener(v -> {
            Intent i = new Intent(Checklist_Activity.this, Checklist_Setup_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        // Refresh the layout if swiped down
        swipeRefreshLayout.setOnRefreshListener(
                () -> swipeRefreshLayout.setRefreshing(false)
        );
    }

    /**
     * This constructor handles the closing of any dialog interfaces that may appear through editing an item or creating a new item. When the dialog closes, it will update all the items and display the newest item at the top of the list.
     *
     * @param dialog – the dialog
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void handleDialogClose(DialogInterface dialog) {
        checklistList = db.getAllItems(profileID);
        Collections.reverse(checklistList);
        checklistAdapter.setItems(checklistList);
        checklistAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieves the profile ID associated with this activity.
     *
     * @return profileID - the profile ID
     */
    public int getProfileID(){return profileID;}

}