package com.example.sids_checklist.checklistreports;

/*
    Uses the expandable recyclerview implementation by
    https://github.com/nikolajakshic/expandablerecycler/tree/master
*/

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.sids_checklist.DialogCloseListener;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * A dialog fragment for exporting checklist data.
 */
public class Checklist_ExportPage extends BottomSheetDialogFragment {
    /**
     * tag for the fragment
     */
    public static final String TAG = "ActionBottomDialog";
    private int profileID;

    /**
     * Creates a new instance of the Checklist_ExportPage fragment.
     *
     * @param start The start date.
     * @param end   The end date.
     * @return A new instance of Checklist_ExportPage.
     */
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
        assert export != null;
        this.profileID = export.getProfileID();

        return view;
    }

    /**
     * Gets the screen width in pixels.
     *
     * @return The screen width.
     */
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

        List<SessionItemView> sessionItemViews = new ArrayList<>();

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

        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(true);
    }

    /**
     *
     * @param dialog the dialog that was dismissed will be passed into the
     *               method
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}

/**
 * Represents a session item view.
 */
class SessionItemView {

    private String date;
    private boolean expanded;
    private final ArrayList<TableRow> rows;

    /**
     * Initializes a new instance of the SessionItemView class with a specified date.
     *
     * @param date The date.
     */
    public SessionItemView(String date)
    {
        this.date = date;
        expanded = false;
        rows = new ArrayList<>();
    }

    /**
     * Adds a row to the session item view.
     *
     * @param row The row to add.
     */
    public void addRow(TableRow row)
    {
        rows.add(row);
    }

    /**
     * Sets the date of the session item view.
     *
     * @param date The date to set.
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * Gets the rows of the session item view.
     *
     * @return The rows.
     */
    public ArrayList<TableRow> getRows()
    {
        return rows;
    }

    /**
     * Sets whether the session item view is expanded.
     *
     * @param expanded True if expanded; otherwise, false.
     */
    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }

    /**
     * Determin if the view is expanded
     *
     * @return True if expanded; otherwise, false.
     */
    public boolean isExpanded()
    {
        return expanded;
    }

    /**
     * Get the selected date from the database
     *
     * @return the date as a string value
     */
    public String getDate()
    {
        return date;
    }
}

/**
 * Adapter class for the RecyclerView in Checklist_ExportPage.
 */
class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {

    private final List<SessionItemView> list;

    /**
     * Constructs a RecAdapter with the specified list of SessionItemView items.
     *
     * @param list The list of SessionItemView items.
     */
    public RecAdapter(List<SessionItemView> list) {
        this.list = list;
    }

    @NonNull
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

    /**
     * @return the list size as an integer
     */
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * ViewHolder class for the RecyclerView.
     */
    static class RecViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final TableLayout table;
        private final View subItem;

        /**
         * Constructs a RecViewHolder with the specified itemView.
         *
         * @param itemView The itemView.
         */
        public RecViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.item_date);
            table = itemView.findViewById(R.id.item_table);
            subItem = itemView.findViewById(R.id.sub_item);
        }

        /**
         * Binds the session item view to the view holder.
         *
         * @param item The session item view.
         */
        private void bind(SessionItemView item) {
            ArrayList<TableRow> rows;

            boolean expanded = item.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            date.setText(item.getDate());
            rows = item.getRows();
            table.removeAllViews();
            for (int i = 0; i < rows.size(); i++) {
                table.addView(rows.get(i));
            }
        }
    }
}