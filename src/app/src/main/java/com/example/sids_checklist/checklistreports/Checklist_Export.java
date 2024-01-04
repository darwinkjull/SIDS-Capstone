package com.example.sids_checklist.checklistreports;

import static android.graphics.Color.parseColor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sids_checklist.R;
import android.widget.TextView;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Checklist_Export extends AppCompatActivity {
    TextView selectedDate;
    Button datePicker, exportReturn, exportConfirm;

    Long startDate, endDate;
    MutableLiveData<Long> startDateListener = new MutableLiveData<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_export);
        // Hide action bar so top most navigation is hidden
        Objects.requireNonNull(getSupportActionBar()).hide();


        selectedDate = findViewById(R.id.selectDate);
        datePicker = findViewById(R.id.datePick);
        exportReturn = findViewById(R.id.exportReturn);
        exportConfirm = findViewById(R.id.exportConfirm);


        startDateListener.setValue(null);
        startDateListener.observe(this , changedValue -> {
            if((startDate == null || endDate == null)){
                exportConfirm.setEnabled(false);
                exportConfirm.setBackgroundColor(Color.GRAY);
            } else {
                exportConfirm.setEnabled(true);
                exportConfirm.setBackgroundColor(parseColor("#A2C579"));
            }
        });

        // Setting click listener for the date picker button
        datePicker.setOnClickListener(view -> DatePickerdialog());

        exportConfirm.setOnClickListener(
                v -> Checklist_ExportPage.newInstance(startDate, endDate).show(getSupportFragmentManager(),
                        Checklist_ExportPage.TAG));

        exportReturn.setOnClickListener(v -> startActivity(new Intent(Checklist_Export.this,
                Checklist_Reports.class)));
    }

    private void DatePickerdialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        // Building the date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            // Retrieving the selected start and end dates
            startDate = selection.first;
            endDate = selection.second;

            // Formating the selected dates as strings
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            // Creating the date range string
            String selectedDateRange = startDateString + " - " + endDateString;

            // Displaying the selected date range in the TextView
            selectedDate.setText(selectedDateRange);
            startDateListener.setValue(startDate);
        });

        // Showing the date picker dialog
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}
