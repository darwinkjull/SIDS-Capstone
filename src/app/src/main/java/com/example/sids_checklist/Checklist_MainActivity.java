package com.example.sids_checklist;

/*
Code created with reference to Mohit Singh's To Do List App Android Studio Tutorial
https://github.com/msindev/Do-it

*/

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sids_checklist.checklistadapter.ChecklistAdapter;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
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

        // Hide action bar so top most navigation is hidden
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Create database within Main Function and open
        db = new Checklist_DatabaseHandler(this);
        db.openDatabase();

        // On startup, initialize new empty array of checklist items
        checklistList = new ArrayList<>();

        // reference recyclerview item in checklist_activitymain.xml
        // Initialize variables to be used by RecylcerView adaptive list API
        RecyclerView checklistRecyclerView = findViewById(R.id.checklistRecyclerView);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        checklistAdapter = new ChecklistAdapter(db, this);
        checklistRecyclerView.setAdapter(checklistAdapter);

        // Add the "ADD" "button capability onto screen
        FloatingActionButton fab = findViewById(R.id.checklistFAB);

        // add item creator helper to reference in main using recyclerview api
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new Checklist_RecyclerItemTouchHelper(checklistAdapter));
        itemTouchHelper.attachToRecyclerView(checklistRecyclerView);

        // display current items in the database (newest first)
        checklistList = db.getAllItems();
        Collections.reverse(checklistList);
        checklistAdapter.setItems(checklistList);

        // listen for "ADD" button being pressed by user
        // if pressed, continue to Item adding functionality in Checklist_AddNewItem
        fab.setOnClickListener(
                v -> Checklist_AddNewItem.newInstance().show(getSupportFragmentManager(),
                        Checklist_AddNewItem.TAG));
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
