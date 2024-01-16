package com.example.sids_checklist.checklistreports;

/*
Creating a reports page to output the progress of the checklist
-- This is a very temporary setup, cannot access database yet

 */

import static android.graphics.Color.parseColor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sids_checklist.Main_Activity;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Checklist_Reports extends AppCompatActivity {
    ArrayList<Entry> lineArrayList;
    ArrayList<String> sessionList;
    private int profileID;
    private String profileUsername;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_report);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Get the profile ID that was passed into the activity using the intent
        profileID = getIntent().getIntExtra("profile_id", -1);
        assert (profileID != -1);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(this);
        profileUsername = profile_db.getUsernameByID(profileID);

        LineChart lineChart = findViewById(R.id.checklistChart);

        // Add the "REPORTS" "button capability onto screen
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button ChecklistButton = findViewById(R.id.checklistButton);
        Button ExportButton = findViewById(R.id.ExportButton);

        getData();
        getSleepSessions();

        LineDataSet lineDataSet = new LineDataSet(lineArrayList, "Sleep Sessions");
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(sessionList));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-80);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);

        lineChart.setDragEnabled(true);
        lineChart.moveViewToX(lineData.getEntryCount());
        lineChart.setVisibleXRangeMaximum(5);
        lineChart.getXAxis().setAxisMinimum(0);
        lineChart.getAxisLeft().setAxisMinimum(0);
        lineChart.getAxisLeft().setAxisMaximum(100);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraTopOffset(35);

        lineDataSet.setColors(parseColor("#D2DE32"));
        lineDataSet.setCircleColor(parseColor("#A2C579"));
        lineDataSet.setValueTextSize(10); // hide the top text
        lineDataSet.setCircleRadius(8);
        lineDataSet.setCircleHoleColor(parseColor("#A2C579"));
        lineDataSet.setLineWidth(3);

        lineChart.getDescription().setEnabled(false);

        ChecklistButton.setOnClickListener(v -> startActivity(new Intent(Checklist_Reports.this,
                Main_Activity.class)));

        ExportButton.setOnClickListener(v -> startActivity(new Intent(Checklist_Reports.this,
                Checklist_Export.class)));
    }
    private void getData(){
        Checklist_UtilDatabaseHandler disp_db = new Checklist_UtilDatabaseHandler(this);
        disp_db.openDatabase();

        lineArrayList = new ArrayList<>();

        List dataArrayList = disp_db.calculateSessionData(profileUsername);
        AtomicReference<Float> i = new AtomicReference<>((float) 0);

        dataArrayList.forEach(dataSet -> {
            lineArrayList.add(new Entry(i.get(), (Float) dataSet));
            i.updateAndGet(v -> v + 1);
        });
    }

    private void getSleepSessions(){
        Checklist_UtilDatabaseHandler disp_db = new Checklist_UtilDatabaseHandler(this);
        disp_db.openDatabase();
        sessionList = new ArrayList<>();
        ArrayList newSessions = disp_db.getAllSessions(profileUsername);
        newSessions.forEach(session ->{
            String[] tempString = (String.valueOf(session).split(" "));
            sessionList.add(tempString[1] + " " + tempString[2] + " " + tempString[3]);
        });
    }

    public String getProfileUsername(){return profileUsername;}
}
