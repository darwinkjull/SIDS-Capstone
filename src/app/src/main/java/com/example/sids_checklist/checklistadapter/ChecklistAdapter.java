package com.example.sids_checklist.checklistadapter;

/*
Creating an adapter class to support recycler view
- List of items
- activity context fot Checklist_MainActivity
- constructor for adapter
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sids_checklist.Checklist_MainActivity;
import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistmodel.ChecklistModel;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
    private List<ChecklistModel> checklistList;
    private Checklist_MainActivity activity;

    // Pass activity context to adapter
    public ChecklistAdapter(Checklist_MainActivity activity){
        this.activity = activity;
    }

    // define a viewholder which will get context from checklist_layout.xml
    // this will help populate the item list using the parameters from the xml
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_layout, parent, false);
        return new ViewHolder(itemView);
    }

    // get the name and status of the checklist item
    public void onBindViewHolder(ViewHolder holder, int position){
        ChecklistModel item = checklistList.get(position);
        holder.task.setText(item.getItem());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    // helper function to turn int (1/0) into boolean
    private boolean toBoolean(int i){
        return i != 0;
    }

    // update the checklist if new item is added
    public void setItem(List<ChecklistModel> checklistList){
        this.checklistList = checklistList;
        notifyDataSetChanged();
    }

    // get a count of the items currently in the checklist
    public int getItemCount(){
        return checklistList.size();
    }


    // define ViewHolder class for remaining attributes
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder (View view){
            super(view);
            task = view.findViewById(R.id.checklistContent);
        }
    }
}
