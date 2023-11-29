package com.example.sids_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sids_checklist.checklistreports.Checklist_Reports;
import java.util.Objects;

public class Profile_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button returnFromProfileButton = findViewById(R.id.returnFromProfileButton);

        returnFromProfileButton.setOnClickListener(v -> startActivity(new Intent(Profile_Activity.this,
                Main_Activity.class)));

    }
}
