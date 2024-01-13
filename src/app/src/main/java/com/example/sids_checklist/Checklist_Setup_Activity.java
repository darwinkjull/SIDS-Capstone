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
import com.example.sids_checklist.checklistmodel.ChecklistModel;

import java.util.ArrayList;

public class Checklist_Setup_Activity extends AppCompatActivity {

    private RecyclerView tasksRecyclerViewSmoking;
    private RecyclerView tasksRecyclerViewCoSleeping;
    private RecyclerView tasksRecyclerViewStandard;
    private SetUpAdapter tipsAdapterSmoking;
    private SetUpAdapter tipsAdapterCoSleeping;
    private SetUpAdapter tipsAdapterStandard;
    private ArrayList<Tips> smokingTips;
    private ArrayList<Tips> coSleepingTips;
    private ArrayList<Tips> standardTips;
    private Checklist_DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggested_checklist);
        getSupportActionBar().hide();

        db = new Checklist_DatabaseHandler(this);
        db.openDatabase();

        tasksRecyclerViewSmoking = findViewById(R.id.SmokingRecycler);
        tasksRecyclerViewSmoking.setLayoutManager(new LinearLayoutManager(this));

        tasksRecyclerViewCoSleeping = findViewById(R.id.CoSLeepingRecycler);
        tasksRecyclerViewCoSleeping.setLayoutManager(new LinearLayoutManager(this));

        tasksRecyclerViewStandard = findViewById(R.id.StadardRecycler);
        tasksRecyclerViewStandard.setLayoutManager(new LinearLayoutManager(this));

        tipsAdapterSmoking = new SetUpAdapter(this);
        tipsAdapterCoSleeping = new SetUpAdapter(this);
        tipsAdapterStandard = new SetUpAdapter(this);

        tasksRecyclerViewSmoking.setAdapter(tipsAdapterSmoking);
        Tips smoking = new Tips();
        smokingTips = smoking.initSmokingTips();


        tasksRecyclerViewCoSleeping.setAdapter(tipsAdapterCoSleeping);
        Tips coSleeping = new Tips();
        coSleepingTips = coSleeping.initCoSleepingTips();


        tasksRecyclerViewStandard.setAdapter(tipsAdapterStandard);
        Tips standard = new Tips();
        standardTips = standard.initStandardTips();

        tipsAdapterSmoking.setTips(smokingTips);
        tipsAdapterCoSleeping.setTips(coSleepingTips);
        tipsAdapterStandard.setTips(standardTips);

        Button add = findViewById(R.id.suggestedAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckedItems(tipsAdapterSmoking);
                checkCheckedItems(tipsAdapterCoSleeping);
                checkCheckedItems(tipsAdapterStandard);
            }
        });



        Button back = findViewById(R.id.checklistButtonBack);
        back.setOnClickListener(v -> startActivity(new Intent(Checklist_Setup_Activity.this,
                Checklist_Activity.class)));

    }
    private void checkCheckedItems(SetUpAdapter adapter){
        for (int i = 0; i < adapter.getItemCount(); i++){
            Tips item = adapter.getItem(i);

            if (item.getStatus()==1){
                db.insertTip(item);
            }
        }
    }
}
