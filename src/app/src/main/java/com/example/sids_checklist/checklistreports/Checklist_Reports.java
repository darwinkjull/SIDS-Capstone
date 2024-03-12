package com.example.sids_checklist.checklistreports;

/*
Creating a reports page to output the progress of the checklist
-- This is a very temporary setup, cannot access database yet

 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
    private LineChart lineChart;

    private LineDataSet lineDataSet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_report);
        Objects.requireNonNull(getSupportActionBar()).hide();


        // Get the profile ID that was passed into the activity using the intent
        profileID = getIntent().getIntExtra("profile_id", -1);
        assert (profileID != -1);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(this);
        profile_db.openDatabase();
        profileUsername = profile_db.getUsernameByID(profileID);

        List<String> usernameList;
        usernameList = profile_db.getAllUsernames();

        // Set up spinner (drop down menu) to house the profiles we can select
        // The ArrayAdapter is used to put our list of usernames into the drop down menu
        Spinner profile_select = (Spinner) findViewById(R.id.profile_select_reports);
        ArrayAdapter<String> usernameAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, usernameList);
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_select.setAdapter(usernameAdapter);
        if (profileID != -1) {
            profile_select.setSelection(usernameAdapter.getPosition(profile_db.getUsernameByID(profileID)));
        }

        /*
         * This itemSelectedListener will allow us to navigate using the buttons only when
         * an item from the list has been chosen
         */
        profile_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUsername = parent.getItemAtPosition(position).toString();
                // Getting profile ID from the selected user name
                profileID = profile_db.getIDByUsername(selectedUsername);
                getData();
                getSleepSessions();
                String colour = profile_db.getProfileInfoFromID(profileID).getProfile_color();
                int colour_id = R.color.profileColor1;
                switch (colour) {
                    case "profileColor2": colour_id = R.color.profileColor2; break;
                    case "profileColor3": colour_id = R.color.profileColor3; break;
                    case "profileColor4": colour_id = R.color.profileColor4; break;
                    case "profileColor5": colour_id = R.color.profileColor5; break;
                    case "profileColor6": colour_id = R.color.profileColor6; break;
                }

                int colour_real = getResources().getColor(colour_id, null);

                setColoursInvalidateBitmapCache(colour_real);

                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(sessionList));
                lineDataSet.setValues(lineArrayList);
                lineDataSet.notifyDataSetChanged();
                lineChart.moveViewToX(lineDataSet.getEntryCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lineChart = findViewById(R.id.checklistChart);

        // Add the "REPORTS" "button capability onto screen
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button returnFromReportsButton = findViewById(R.id.returnFromReportsButton);
        Button ExportButton = findViewById(R.id.ExportButton);

        getData();
        getSleepSessions();

        lineDataSet = new LineDataSet(lineArrayList, "Sleep Sessions");
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(sessionList));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-80);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);

        String colour = profile_db.getProfileInfoFromID(profileID).getProfile_color();
        int colour_id = R.color.profileColor1;
        switch (colour) {
            case "profileColor2": colour_id = R.color.profileColor2; break;
            case "profileColor3": colour_id = R.color.profileColor3; break;
            case "profileColor4": colour_id = R.color.profileColor4; break;
            case "profileColor5": colour_id = R.color.profileColor5; break;
            case "profileColor6": colour_id = R.color.profileColor6; break;
        }

        int colour_real = getResources().getColor(colour_id, null);
        setColoursInvalidateBitmapCache(colour_real);
        lineDataSet.setValueTextSize(10); // hide the top text
        lineDataSet.setCircleRadius(8);
        lineDataSet.setLineWidth(3);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisLeft().setDrawGridLines(false);

        lineChart.setDragEnabled(true);
        lineChart.moveViewToX(lineDataSet.getEntryCount());
        lineChart.setVisibleXRangeMaximum(5);
        lineChart.getXAxis().setAxisMinimum(0);
        lineChart.getAxisLeft().setAxisMinimum(0);
        lineChart.getAxisLeft().setAxisMaximum(100);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraTopOffset(35);

        returnFromReportsButton.setOnClickListener(v -> {
            Intent i = new Intent(Checklist_Reports.this, Main_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        ExportButton.setOnClickListener(v -> {
            Intent i = new Intent(Checklist_Reports.this, Checklist_Export.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });
    }
    private void getData(){
        Checklist_UtilDatabaseHandler disp_db = new Checklist_UtilDatabaseHandler(this);
        disp_db.openDatabase();

        lineArrayList = new ArrayList<>();

        List dataArrayList = disp_db.calculateSessionData(profileID);
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
        ArrayList newSessions = disp_db.getAllSessions(profileID);
        newSessions.forEach(session ->{
            String[] tempString = (String.valueOf(session).split(" "));
            sessionList.add(tempString[1] + " " + tempString[2] + " " + tempString[3]);
        });
    }

    /**
     * Reset the dataset's bitmap
     * More info https://stackoverflow.com/questions/54350166/mpchart-android-setcirclecolor-not-reflecting
     * @param colour Colour to change the data points to
     */
    private void setColoursInvalidateBitmapCache(int colour)
    {
        lineDataSet.setDrawCircles(true);
        int count = lineDataSet.getCircleColors().size();
        lineDataSet.resetCircleColors();
        lineDataSet.setColors(colour);
        lineDataSet.setCircleColor(colour);
        lineDataSet.setCircleHoleColor(colour);
        if (count == 1) {lineDataSet.getCircleColors().add(colour);}
    }

    public String getProfileUsername(){return profileUsername;}
    public int getProfileID(){return profileID;}
}
