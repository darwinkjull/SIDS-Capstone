package com.example.sids_checklist.checklistreports;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import com.example.sids_checklist.DialogCloseListener;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class Checklist_Export extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private CalendarView endDate;
    private Checklist_UtilDatabaseHandler disp_db;

    private Calendar calendarStart;

    public static Checklist_Export newInstance(){return new Checklist_Export();}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.checklist_export, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        CalendarView startDate = requireView().findViewById(R.id.startDatePicker);
        endDate = requireView().findViewById(R.id.endDatePicker);
        Button exportConfirm = requireView().findViewById(R.id.exportConfirm);

        disp_db = new Checklist_UtilDatabaseHandler(getActivity());
        disp_db.openDatabase();

        startDate.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            endDate.setMinDate(0);
            calendarStart = Calendar.getInstance();
            calendarStart.set(year, month, dayOfMonth);
            endDate.setMinDate(calendarStart.getTimeInMillis());
        });

        exportConfirm.setOnClickListener(v -> dismiss());
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
