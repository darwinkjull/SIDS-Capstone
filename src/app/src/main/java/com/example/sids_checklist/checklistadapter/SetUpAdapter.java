package com.example.sids_checklist.checklistadapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;
import com.example.sids_checklist.Checklist_Setup_Activity;
import com.example.sids_checklist.R;
import com.example.sids_checklist.Tips;

import java.util.ArrayList;

public class SetUpAdapter extends RecyclerView.Adapter<SetUpAdapter.ViewHolder> {
    private ArrayList<Tips> tips;
    private final Checklist_Setup_Activity activity;

    public SetUpAdapter(Checklist_Setup_Activity activity){
        this.activity = activity;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_layout, parent, false);
        return new ViewHolder(itemView);
    }
    public Tips getItem(int position) {
        return tips.get(position);
    }
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tips item = tips.get(position);
        holder.task.setText(item.getItem());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                item.setStatus(1);
            } else {
                item.setStatus(0);
            }
        });
    }


    public int getItemCount(){
        return tips.size();
    }
    // helper function to turn int (1/0) into boolean
    private boolean toBoolean(int i){
        return i != 0;
    }

    public void setTips(ArrayList<Tips> list){
        this.tips = list;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checklistContent);
        }
    }
}
    
