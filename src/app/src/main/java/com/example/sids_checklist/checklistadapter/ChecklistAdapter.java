package com.example.sids_checklist.checklistadapter;

/*
Creating an adapter class to support recycler view
- List of items
- activity context fot Main_Activity
- constructor for adapter
 */

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

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
    private List<ChecklistModel> checklistList;
    private final Checklist_Activity activity;
    private final Checklist_DatabaseHandler db;
    private final Checklist_UtilDatabaseHandler disp_db;

    // Pass activity context to adapter
    public ChecklistAdapter(Checklist_DatabaseHandler db, Checklist_UtilDatabaseHandler disp_db, Checklist_Activity activity) {
        this.db = db;
        this.disp_db = disp_db;
        this.activity = activity;
    }

    // define a viewholder which will get context from checklist_layout.xml
    // this will help populate the item list using the parameters from the xml
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_layout, parent, false);
        return new ViewHolder(itemView);
    }

    // get the name and status of the checklist item
    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        ChecklistModel item = checklistList.get(position);
        holder.item.setText(item.getItem());
        holder.item.setChecked(toBoolean(item.getStatus()));
        holder.item.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                db.updateStatus(item.getId(), 1, activity.getProfileUsername());
                item.setStatus(1);
            } else {
                db.updateStatus(item.getId(), 0, activity.getProfileUsername());
                item.setStatus(0);
            }
        });
    }

    // helper function to turn int (1/0) into boolean
    private boolean toBoolean(int i) {
        return i != 0;
    }

    // update the checklist if new item is added
    @SuppressLint("NotifyDataSetChanged")
    public void setItem(List<ChecklistModel> checklistList) {
        this.checklistList = checklistList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshItems(List<ChecklistModel> checklistList) {
        disp_db.openDatabase();
        String session = String.valueOf(Calendar.getInstance().getTime());
        checklistList.forEach((item) -> {
            disp_db.insertItem(item.getItem(), item.getStatus(), session, activity.getProfileUsername());
        });
        checklistList.forEach((item) -> {
            db.updateStatus(item.getId(), 0, activity.getProfileUsername());
            item.setStatus(0);
        });
        notifyDataSetChanged();
    }

    // get a count of the items currently in the checklist
    public int getItemCount() {
        return checklistList.size();
    }

    // update the checklist with new and current items
    public void setItems(List<ChecklistModel> checklistList) {
        this.checklistList = checklistList;
    }

    // delete items from checklist
    public void deleteItem(int position) {
        ChecklistModel item = checklistList.get(position);
        db.deleteItem(item.getId(), activity.getProfileUsername());
        checklistList.remove(position);
        notifyItemRemoved(position);
    }

    // edit the title of an existing checklist item
    public void editItem(int position) {
        ChecklistModel item = checklistList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("item", item.getItem());
        Checklist_AddNewItem fragment = new Checklist_AddNewItem();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), Checklist_AddNewItem.TAG);
    }

    public Context getContext() {
        return activity;
    }

    // define ViewHolder class for remaining attributes
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox item;

        ViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.checklistContent);
        }
    }
}
