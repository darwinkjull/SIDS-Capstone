package com.example.sids_checklist;

/*
Code created with reference to Mohit Singh's To Do List App Android Studio Tutorial

This is the main activity which allows the user to access the sleeping checklist,

*/

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sids_checklist.checklistadapter.ChecklistAdapter;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Checklist_MainActivity extends AppCompatActivity implements DialogCloseListener{
    private ChecklistAdapter checklistAdapter;
    private List<ChecklistModel> checklistList;

    private Checklist_DatabaseHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_activitymain);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);

        // Hide action bar so top most navigation is hidden
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Create database within Main Function and open
        db = new Checklist_DatabaseHandler(this);
        db.openDatabase();

        Checklist_UtilDatabaseHandler disp_db = new Checklist_UtilDatabaseHandler(this);
        disp_db.openDatabase();

        // On startup, initialize new empty array of checklist items
        checklistList = new ArrayList<>();

        // reference recyclerview item in checklist_activitymain.xml
        // Initialize variables to be used by RecylcerView adaptive list API
        RecyclerView checklistRecyclerView = findViewById(R.id.checklistRecyclerView);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        checklistAdapter = new ChecklistAdapter(db, disp_db,this);
        checklistRecyclerView.setAdapter(checklistAdapter);

        // Add the "ADD" "button capability onto screen
        FloatingActionButton fab = findViewById(R.id.checklistFAB);

        // Add the "REPORTS" "button capability onto screen
        Button reportButton = findViewById(R.id.checklistReportButton);

        // add item creator helper to reference in main using recyclerview api
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new Checklist_RecyclerItemTouchHelper(checklistAdapter));
        itemTouchHelper.attachToRecyclerView(checklistRecyclerView);

        // display current items in the database (newest first)
        checklistList = db.getAllItems();
        Collections.reverse(checklistList);
        // checklistAdapter.refreshItems(checklistList);
        checklistAdapter.setItems(checklistList);

        // listen for "ADD" button being pressed by user
        // if pressed, continue to Item adding functionality in Checklist_AddNewItem
        fab.setOnClickListener(
                v -> Checklist_AddNewItem.newInstance().show(getSupportFragmentManager(),
                        Checklist_AddNewItem.TAG));

        reportButton.setOnClickListener(v -> startActivity(new Intent(Checklist_MainActivity.this,
                Checklist_Reports.class)));

        // Refresh  the layout if swiped down, to begin a new napping session
        // TODO: Confirmation check
        swipeRefreshLayout.setOnRefreshListener(
            () -> {
                checklistAdapter.refreshItems(checklistList);
                swipeRefreshLayout.setRefreshing(false);
            }
        );
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void handleDialogClose(DialogInterface dialog){
        checklistList = db.getAllItems();
        Collections.reverse(checklistList);
        checklistAdapter.setItems(checklistList);
        checklistAdapter.notifyDataSetChanged();
    }
}
