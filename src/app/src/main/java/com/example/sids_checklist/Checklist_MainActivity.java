package com.example.sids_checklist;

/*
Code sourced with reference to Mohit Singh's To Do List App Android Studio Tutorial
https://github.com/msindev
*/

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sids_checklist.checklistadapter.ChecklistAdapter;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import java.util.ArrayList;
import java.util.List;

public class Checklist_MainActivity extends AppCompatActivity {
    // Initialize variables to be used by RecylcerView adaptive list API
    private RecyclerView checklistRecyclerView;
    private ChecklistAdapter checklistAdapter;
    private List<ChecklistModel> checklistList;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_activitymain);
        getSupportActionBar().hide(); // Hide action bar so top most navigation is hidden

        // On startup, initialize new empty array of checklist items
        checklistList = new ArrayList<>();

        // reference recyclerview item in checklist_activitymain.xml
        checklistRecyclerView = findViewById(R.id.checklistRecyclerView);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        checklistAdapter = new ChecklistAdapter(this);
        checklistRecyclerView.setAdapter(checklistAdapter);

        // Temporary checklist item creation for demonstration
        ChecklistModel item_1 = new ChecklistModel();
        item_1.setItem("Clear Crib of Debris");
        item_1.setStatus(0);
        item_1.setId(1);

        ChecklistModel item_2 = new ChecklistModel();
        item_2.setItem("Secure Loose Bedding");
        item_2.setStatus(0);
        item_2.setId(1);

        ChecklistModel item_3 = new ChecklistModel();
        item_3.setItem("Crib in the same room as Parents");
        item_3.setStatus(0);
        item_3.setId(1);

        ChecklistModel item_4 = new ChecklistModel();
        item_4.setItem("Prone Sleeping Position");
        item_4.setStatus(0);
        item_4.setId(1);

        ChecklistModel item_5 = new ChecklistModel();
        item_5.setItem("Appropriate Clothing to Prevent Overheating");
        item_5.setStatus(0);
        item_5.setId(1);

        checklistList.add(item_1);
        checklistList.add(item_2);
        checklistList.add(item_3);
        checklistList.add(item_4);
        checklistList.add(item_5);

        checklistAdapter.setItem(checklistList);

    }
}
