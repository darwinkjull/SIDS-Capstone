package com.example.sids_checklist.checklistadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.Checklist_Activity;
import com.example.sids_checklist.Checklist_AddNewItem;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import com.example.sids_checklist.checklistutils.Checklist_UtilDatabaseHandler;

import java.util.List;
import java.util.Calendar;

/**
 * The ChecklistAdapter class is responsible for managing the checklist items in a RecyclerView.
 */
public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
    private List<ChecklistModel> checklistList;
    private final Checklist_Activity activity;
    private final Checklist_DatabaseHandler db;
    private final Checklist_UtilDatabaseHandler disp_db;

    // Pass activity context to adapter
    /**
     * Constructs a ChecklistAdapter.
     *
     * @param db         The database handler for checklist items.
     * @param disp_db    The database handler for displaying checklist items.
     * @param activity   The activity associated with the adapter.
     */
    public ChecklistAdapter(Checklist_DatabaseHandler db, Checklist_UtilDatabaseHandler disp_db, Checklist_Activity activity) {
        this.db = db;
        this.disp_db = disp_db;
        this.activity = activity;
    }

    /**
     * Creates a ViewHolder by inflating the checklist_layout.xml.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type.
     * @return The ViewHolder instance.
     */
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_layout, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Binds data to the ViewHolder.
     *
     * @param holder   The ViewHolder instance.
     * @param position The position of the item in the list.
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        ChecklistModel item = checklistList.get(position);
        holder.item.setText(item.getItem());
        holder.item.setChecked(toBoolean(item.getStatus()));
        holder.item.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                db.updateStatus(item.getId(), 1, activity.getProfileID());
                item.setStatus(1);
            } else {
                db.updateStatus(item.getId(), 0, activity.getProfileID());
                item.setStatus(0);
            }
        });
    }

    /**
     * Converts an integer to a boolean.
     *
     * @param i The integer value.
     * @return True if the integer is non-zero, false otherwise.
     */
    private boolean toBoolean(int i) {
        return i != 0;
    }

    /**
     * Sets the checklist items.
     *
     * @param checklistList The list of checklist items.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setItem(List<ChecklistModel> checklistList) {
        this.checklistList = checklistList;
        notifyDataSetChanged();
    }

    /**
     * Refreshes the checklist items with updated data.
     *
     * @param checklistList The list of updated checklist items.
     *                      It contains the new data to be refreshed.
     *                      Each ChecklistModel object in the list represents an item in the checklist.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void refreshItems(List<ChecklistModel> checklistList) {
        disp_db.openDatabase();
        String session = String.valueOf(Calendar.getInstance().getTime());
        checklistList.forEach((item) -> disp_db.insertItem(item.getItem(), item.getStatus(), session, activity.getProfileID()));
        checklistList.forEach((item) -> {
            db.updateStatus(item.getId(), 0, activity.getProfileID());
            item.setStatus(0);
        });
        notifyDataSetChanged();
    }

    // get a count of the items currently in the checklist
    public int getItemCount() {
        return checklistList.size();
    }

    /**
     * update the checklist with new and current items
     *
     * @param checklistList - the checklist object
     */
    public void setItems(List<ChecklistModel> checklistList) {
        this.checklistList = checklistList;
    }

    /**
     * delete items from checklist
     * @param position - the position of the item in the layout
     */
    public void deleteItem(int position) {
        ChecklistModel item = checklistList.get(position);
        db.deleteItem(item.getId(), activity.getProfileID());
        checklistList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * edit the title of an existing checklist item
     *
     * @param position - the position of the item in the layout
     */
    public void editItem(int position) {
        ChecklistModel item = checklistList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("item", item.getItem());
        Checklist_AddNewItem fragment = new Checklist_AddNewItem();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), Checklist_AddNewItem.TAG);
    }

    /**
     * get the context for the activity
     *
     * @return this activity
     */
    public Context getContext() {
        return activity;
    }

    /**
     * define ViewHolder class for remaining attributes
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox item;

        ViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.checklistContent);
        }
    }
}
