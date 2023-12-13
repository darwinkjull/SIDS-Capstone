package com.example.sids_checklist.checklistreports;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import java.util.Calendar;
import java.util.Objects;

public class Checklist_Export extends AppCompatActivity {
    private CalendarView endDate;
    private Calendar calendarStart;
    private Calendar calendarEnd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_export);
        Objects.requireNonNull(getSupportActionBar()).hide();

        CalendarView startDate = findViewById(R.id.startDatePicker);
        endDate = findViewById(R.id.endDatePicker);
        Button exportConfirm = findViewById(R.id.exportConfirm);
        Button exportReturn = findViewById(R.id.exportReturn);

        Checklist_UtilDatabaseHandler disp_db = new Checklist_UtilDatabaseHandler(this);
        disp_db.openDatabase();

        startDate.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            endDate.setMinDate(0);
            calendarStart = Calendar.getInstance();
            calendarStart.set(year, month, dayOfMonth);
            endDate.setMinDate(calendarStart.getTimeInMillis());
        });

        endDate.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendarEnd = Calendar.getInstance();
            calendarEnd.set(year, month, dayOfMonth);
        });

        exportConfirm.setOnClickListener(
                v -> Checklist_ExportPage.newInstance(calendarStart, calendarEnd).show(getSupportFragmentManager(),
                        Checklist_ExportPage.TAG));

        exportReturn.setOnClickListener(v -> startActivity(new Intent(Checklist_Export.this,
                Checklist_Reports.class)));
    }
}
