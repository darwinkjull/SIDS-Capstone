package com.example.sids_checklist.checklistadapter;

/*
Creating an adapter class to support recycler view
- List of items
- activity context fot Checklist_MainActivity
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
import com.example.sids_checklist.Checklist_AddNewItem;
import com.example.sids_checklist.Checklist_MainActivity;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import com.example.sids_checklist.checklistutils.Checklist_DatabaseHandler;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
    private List<ChecklistModel> checklistList;
    private final Checklist_MainActivity activity;
    private final Checklist_DatabaseHandler db;

    // Pass activity context to adapter
    public ChecklistAdapter(Checklist_DatabaseHandler db, Checklist_MainActivity activity){
        this.db = db;
        this.activity = activity;
    }

    // define a viewholder which will get context from checklist_layout.xml
    // this will help populate the item list using the parameters from the xml
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_layout, parent, false);
        return new ViewHolder(itemView);
    }

    // get the name and status of the checklist item
    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ChecklistModel item = checklistList.get(position);
        holder.item.setText(item.getItem());
        holder.item.setChecked(toBoolean(item.getStatus()));
        holder.item.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                db.updateStatus(item.getId(), 1);
            } else {
                db.updateStatus(item.getId(), 0);
            }
        });
    }

    // helper function to turn int (1/0) into boolean
    private boolean toBoolean(int i){
        return i != 0;
    }

    // update the checklist if new item is added
    @SuppressLint("NotifyDataSetChanged")
    public void setItem(List<ChecklistModel> checklistList){
        this.checklistList = checklistList;
        notifyDataSetChanged();
    }

    // get a count of the items currently in the checklist
    public int getItemCount(){
        return checklistList.size();
    }

    // update the checklist with new and current items
    public void setItems(List<ChecklistModel> checklistList){
        this.checklistList = checklistList;
    }

    // delete items from checklist
    public void deleteItem(int position){
        ChecklistModel item = checklistList.get(position);
        db.deleteItem(item.getId());
        checklistList.remove(position);
        notifyItemRemoved(position);
    }

    // edit the title of an existing checklist item
    public void editItem(int position){
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
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox item;

        ViewHolder (View view){
            super(view);
            item = view.findViewById(R.id.checklistContent);
        }
    }
}
