package com.example.sids_checklist.checklistadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.R;
import com.example.sids_checklist.Tips;

import java.util.ArrayList;

/**
 * The SetUpAdapter class manages a list of tasks in a RecyclerView for setup checklist.
 */
public class SetUpAdapter extends RecyclerView.Adapter<SetUpAdapter.ViewHolder> {
    private ArrayList<Tips> tips;

    /**
     * Constructs a SetUpAdapter with the associated activity.
     *
     */
    public SetUpAdapter(){
    }

    /**
     *Inflates the layout from checklist_layout.xml and creates a new ViewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return the viewholder
     */
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_layout, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Returns the tip item
     *
     * @param position the position of the tip
     * @return the item
     */
    public Tips getItem(int position) {
        return tips.get(position);
    }

    /**
     * Binds data to the ViewHolder and handles checkbox state changes.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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

    /**
     * @return the number of items as an integer
     */
    public int getItemCount(){
        return tips.size();
    }

    /**
     * helper function to turn an int into a bool
     *
     * @param i the integer to convert
     * @return a boolean version of the integer
     */
    private boolean toBoolean(int i){
        return i != 0;
    }

    /**
     * Sets the list of tips for the adapter.
     *
     * @param list the list of tips
     */
    public void setTips(ArrayList<Tips> list){
        this.tips = list;
    }

    /**
     * ViewHolder class for caching view components of item layout.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checklistContent);
        }
    }
}
    
