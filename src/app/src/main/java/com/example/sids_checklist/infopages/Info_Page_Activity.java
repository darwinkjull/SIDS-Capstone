package com.example.sids_checklist.infopages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.Main_Activity;
import com.example.sids_checklist.R;

import java.util.Objects;

/**
 * This class represents the activity for displaying information pages.
 * It extends AppCompatActivity to provide compatibility with older Android versions.
 * The activity allows users to navigate to different information sections: sleep location, sleep area, and general care.
 * It includes buttons to navigate to each section.
 */
public class Info_Page_Activity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button backButton = findViewById(R.id.InfoBack);
        Button locationButton = findViewById(R.id.buttonSleepLocation);
        Button areaButton = findViewById(R.id.buttonSleepArea);
        Button generalButton = findViewById(R.id.buttonGenCare);

        backButton.setOnClickListener(v-> startActivity(new Intent(Info_Page_Activity.this, Main_Activity.class)));
        locationButton.setOnClickListener(v-> startActivity(new Intent(Info_Page_Activity.this, Sleep_Location_Activity.class)));
        areaButton.setOnClickListener(v-> startActivity(new Intent(Info_Page_Activity.this, Sleep_Area_Activity.class)));
        generalButton.setOnClickListener(v-> startActivity(new Intent(Info_Page_Activity.this, General_Info_Activity.class)));
    }


}




