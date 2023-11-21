package com.example.sids_checklist;

/*
Code created with reference to Mohit Singh's To Do List App Android Studio Tutorial

This is the main activity which allows the user to access the sleeping checklist,

*/

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Main_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button goToChecklist = findViewById(R.id.goToChecklist);
        goToChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navigationController = Navigation.findNavController(Main_Activity.this, R.id.nav_host_fragment);
                navigationController.navigate(R.id.mainToChecklist);
            }
        });


    }
}