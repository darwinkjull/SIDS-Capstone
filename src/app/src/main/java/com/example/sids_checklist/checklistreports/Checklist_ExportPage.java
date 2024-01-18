package com.example.sids_checklist.checklistreports;

import static android.graphics.Color.parseColor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private int profileID;
    public static Checklist_ExportPage newInstance(long start, long end){
        Checklist_ExportPage CLP = new Checklist_ExportPage();
        Bundle args = new Bundle();
        args.putLong("start", start);
        args.putLong("end", end);
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

        Checklist_Reports reports =  (Checklist_Reports) getActivity();
        profileID = reports.getProfileID();
        return view;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @SuppressLint({"ResourceAsColor", "SimpleDateFormat", "ResourceType", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        TableLayout tl = requireView().findViewById(R.id.export_table);

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

        TextView label_Item = new TextView(getActivity());
        label_Item.setId(TextID);
        label_Item.setAllCaps(true);
        label_Item.setText("Checklist Item");
        label_Item.setPadding(5, 5, 5, 5);
        tr_head.addView(label_Item);

        TextView label_Status = new TextView(getActivity());
        label_Status.setId(TextID + 1);
        label_Status.setText("Status");
        label_Status.setAllCaps(true);
        label_Status.setPadding(10, 5, 5, 5);
        tr_head.addView(label_Status);

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        while (currDate.getTimeInMillis() <= endDate.getTimeInMillis()){

            List[] sessionVars = disp_db.selectSessionData(String.valueOf(currDate.getTime()), profileID);
            TextView[] textArray = new TextView[sessionVars[0].size() + 1];
            TableRow[] tr_heads = new TableRow[sessionVars[0].size() + 1];

            String currSession = "";

            for(int i=0; i<sessionVars[0].size();i++) {
                String sessionDate = String.valueOf(sessionVars[0].get(i));
                String itemName = String.valueOf(sessionVars[1].get(i));
                String status = String.valueOf(sessionVars[2].get(i));
                String statusText = "Incomplete";

                int color = parseColor("#cf7878");

                if (status.equals("1")) {
                    color = parseColor("#A2C579");
                    statusText = "Completed";
                }

                if (!currSession.equals(sessionDate)) {
                    currSession = sessionDate;

                    tr_heads[i] = new TableRow(getActivity());
                    tr_heads[i].setId(i+1);
                    tr_heads[i].setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    textArray[i] = new TextView(getActivity());
                    textArray[i].setId(i + 111);
                    textArray[i].setText(sessionDate);
                    textArray[i].setTextColor(parseColor("#016A70"));
                    textArray[i].setPadding(25, 5, 5, 5);
                    textArray[i].setMaxWidth(getScreenWidth()/2);
                    tr_heads[i].addView(textArray[i]);

                    tl.addView(tr_heads[i], new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                }
                tr_heads[i] = new TableRow(getActivity());
                tr_heads[i].setId(i + 1);
                tr_heads[i].setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                textArray[i] = new TextView(getActivity());
                textArray[i].setId(i + 111);
                textArray[i].setText(itemName);
                textArray[i].setTextColor(color);
                textArray[i].setPadding(25, 5, 5, 5);
                textArray[i].setMaxWidth(getScreenWidth() / 2);
                tr_heads[i].addView(textArray[i]);

                textArray[i] = new TextView(getActivity());
                textArray[i].setId(i + 111);
                textArray[i].setText(statusText);
                textArray[i].setTextColor(color);
                textArray[i].setPadding(5, 5, 5, 5);
                tr_heads[i].addView(textArray[i]);

                tl.addView(tr_heads[i], new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
            }
            currDate.add(Calendar.DATE, 1);
        }
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
