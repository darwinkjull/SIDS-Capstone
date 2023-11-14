package com.example.sids_checklist;

/*
Creating a reports page to output the progress of the checklist
-- This is a very temporary setup, cannot access database yet

TODO: access database to collect data
TODO: rename the top of the page

 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Checklist_Reports extends AppCompatActivity {
    ArrayList<BarEntry> barArrayList;
    ArrayList<String> sessionList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_report);
        BarChart barChart = findViewById(R.id.checklistChart);

        // Add the "REPORTS" "button capability onto screen
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button ChecklistButton = findViewById(R.id.checklistButton);

        getData();
        getSleepSessions();

        BarDataSet barDataSet = new BarDataSet(barArrayList, "Sleep Sessions");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(sessionList));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        barChart.getXAxis().setAxisMinimum(0);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        ChecklistButton.setOnClickListener(v -> startActivity(new Intent(Checklist_Reports.this,
                Checklist_MainActivity.class)));
    }
    private void getData(){
        barArrayList = new ArrayList<>();
        barArrayList.add(new BarEntry(1f, 10));
    }

    private void getSleepSessions(){
        sessionList = new ArrayList<>();
        sessionList.add("Tue Nov 14");
    }
}
