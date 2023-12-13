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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.sids_checklist.DialogCloseListener;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Checklist_ExportPage extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    public static Checklist_ExportPage newInstance(Calendar start, Calendar end){
        Checklist_ExportPage CLP = new Checklist_ExportPage();
        Bundle args = new Bundle();
        args.putLong("start", start.getTimeInMillis());
        args.putLong("end", end.getTimeInMillis());
        CLP.setArguments(args);
        return CLP;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.checklist_exportpage, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @SuppressLint({"ResourceAsColor", "SimpleDateFormat", "ResourceType", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        TableLayout tl = (TableLayout) requireView().findViewById(R.id.export_table);

        Button Confirm = requireView().findViewById(R.id.Export_Confirm);

        Checklist_UtilDatabaseHandler disp_db = new Checklist_UtilDatabaseHandler(getActivity());
        disp_db.openDatabase();

        Bundle args = getArguments();
        assert args != null;
        long start= args.getLong("start", 0);
        long end= args.getLong("end", 0);

        Calendar currDate = Calendar.getInstance();
        currDate.setTimeInMillis(start);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(end);

        int rowID = 10;
        int TextID = 20;

        TableRow tr_head = new TableRow(getActivity());
        tr_head.setId(rowID);
        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView label_Date = new TextView(getActivity());
        label_Date.setId(TextID);
        label_Date.setAllCaps(true);
        label_Date.setText("Date");
        label_Date.setPadding(5, 5, 5, 5);
        tr_head.addView(label_Date);

        TextView label_Item = new TextView(getActivity());
        label_Item.setId(TextID + 1);
        label_Item.setText("Checklist Item");
        label_Item.setAllCaps(true);
        label_Item.setPadding(10, 5, 5, 5);
        tr_head.addView(label_Item);

        TextView label_Status = new TextView(getActivity());
        label_Status.setId(TextID + 2);
        label_Status.setAllCaps(true);
        label_Status.setText("Status");
        label_Status.setPadding(10, 5, 5, 5);
        tr_head.addView(label_Status);

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        while (currDate.getTimeInMillis() <= endDate.getTimeInMillis()){

            List[] sessionVars = disp_db.selectSessionData(String.valueOf(currDate.getTime()));
            TextView[] textArray = new TextView[sessionVars[0].size()];
            TableRow[] tr_heads = new TableRow[sessionVars[0].size()];

            for(int i=0; i<sessionVars[0].size();i++) {
                String sessionDate = String.valueOf(sessionVars[0].get(i));
                String itemName = String.valueOf(sessionVars[1].get(i));
                String status = String.valueOf(sessionVars[2].get(i));

                String itemStatus = "No";

                if (status.equals("1")) {
                    itemStatus = "Yes";
                }

                tr_heads[i] = new TableRow(getActivity());
                tr_heads[i].setId(i + 1);
                tr_heads[i].setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                textArray[i] = new TextView(getActivity());
                textArray[i].setId(i + 111);
                textArray[i].setText(sessionDate);
                textArray[i].setPadding(5, 5, 5, 5);
                tr_heads[i].addView(textArray[i]);

                textArray[i] = new TextView(getActivity());
                textArray[i].setId(i + 111);
                textArray[i].setText(itemName);
                textArray[i].setPadding(25, 5, 5, 5);
                tr_heads[i].addView(textArray[i]);

                textArray[i] = new TextView(getActivity());
                textArray[i].setId(i + 111);
                textArray[i].setText(itemStatus);
                textArray[i].setPadding(25, 5, 5, 5);
                tr_heads[i].addView(textArray[i]);

                tl.addView(tr_heads[i], new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
            }
            currDate.add(Calendar.DATE, 1);
        }

        Confirm.setOnClickListener(v -> dismiss());
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
