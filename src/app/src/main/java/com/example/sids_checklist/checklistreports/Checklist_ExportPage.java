package com.example.sids_checklist.checklistreports;

import static android.graphics.Color.parseColor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Session2Command;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.sids_checklist.DialogCloseListener;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        Checklist_Export export =  (Checklist_Export) getActivity();
        this.profileID = export.getProfileID();

        return view;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @SuppressLint({"ResourceAsColor", "SimpleDateFormat", "ResourceType", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

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

        List<SessionItemView> sessionItemViews = new ArrayList<SessionItemView>();

        boolean complete = true;
        while (currDate.getTimeInMillis() <= endDate.getTimeInMillis()){

            List[] sessionVars = disp_db.selectSessionData(String.valueOf(currDate.getTime()), profileID);
            if (sessionVars[0].size() == 0) {
                currDate.add(Calendar.DATE, 1);
                continue;
            }

            String date_time = String.valueOf(sessionVars[0].get(0));
            SessionItemView itemView = new SessionItemView(date_time);

            for(int i = 0; i < sessionVars[0].size(); i++) {
                String sessionDate = String.valueOf(sessionVars[0].get(i));
                if (!date_time.equals(sessionDate)) {
                    if (complete) {
                        itemView.setDate(itemView.getDate() + "      Completed");
                    } else {
                        itemView.setDate(itemView.getDate() + "      Incomplete");
                    }
                    date_time = sessionDate;
                    sessionItemViews.add(itemView);
                    itemView = new SessionItemView(date_time);
                    complete = true;
                }

                String itemName = String.valueOf(sessionVars[1].get(i));

                String statusText;
                int colour;
                if (String.valueOf(sessionVars[2].get(i)).equals("1")) {
                    statusText = "Completed";
                    colour = parseColor("#A2C579");
                } else {
                    statusText = "Incomplete";
                    complete = false;
                    colour = parseColor("#cf7878");
                }

                TableRow row = new TableRow(getActivity());

                TextView checklist_item = new TextView(getActivity());
                checklist_item.setId(i);
                checklist_item.setText(itemName);
                checklist_item.setTextColor(colour);
                checklist_item.setPadding(25, 5, 5, 5);
                checklist_item.setMaxWidth(getScreenWidth() / 3 * 2);
                row.addView(checklist_item);

                TextView status_item = new TextView(getActivity());
                status_item.setId(i);
                status_item.setText(statusText);
                status_item.setTextColor(colour);
                status_item.setPadding(5, 5, 5, 5);
                row.addView(status_item);

                itemView.addRow(row);
            }

            currDate.add(Calendar.DATE, 1);

        }

        RecAdapter adapter = new RecAdapter(sessionItemViews);
        RecyclerView recyclerView = view.findViewById(R.id.receive_view);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(true);
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

class SessionItemView {

    private String date;
    private boolean expanded;
    private ArrayList<TableRow> rows;

    public SessionItemView(String date)
    {
        this.date = date;
        expanded = false;
        rows = new ArrayList<>();
    }

    public void addRow(TableRow row)
    {
        rows.add(row);
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public ArrayList<TableRow> getRows()
    {
        return rows;
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    public String getDate()
    {
        return date;
    }
}

class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {

    private List<SessionItemView> list;

    public RecAdapter(List<SessionItemView> list) {
        this.list = list;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.session_item, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        SessionItemView movie = list.get(position);

        holder.bind(movie);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = movie.isExpanded();
            movie.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TableLayout table;
        private View subItem;

        public RecViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.item_date);
            table = itemView.findViewById(R.id.item_table);
            subItem = itemView.findViewById(R.id.sub_item);
        }

        private void bind(SessionItemView movie) {
            ArrayList<TableRow> rows;

            boolean expanded = movie.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            date.setText(movie.getDate());
            rows = movie.getRows();
            table.removeAllViews();
            for (int i = 0; i < rows.size(); i++) {
                table.addView(rows.get(i));
            }
        }
    }
}