package com.example.sids_checklist.checklistadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.Info_Page_Activity;
import com.example.sids_checklist.R;
import com.example.sids_checklist.Topics;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private ArrayList<Topics> topics;

    private final Info_Page_Activity activity;

    public InfoAdapter(Info_Page_Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_tiles, parent, false);
        return new InfoAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.ViewHolder holder, int position) {
        Topics item = topics.get(position);
        TextView title = holder.titleView;
        title.setText(item.getTitle());
        ImageView icon = holder.iconView;
        icon.setImageResource(item.getImage());

    }
    public void setTopics(ArrayList<Topics> list){
        this.topics = list;
    }
    @Override
    public int getItemCount() {
        return topics.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleView;
        public ImageView iconView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.titleInfo);

            iconView = (ImageView) itemView.findViewById(R.id.imageInfo);
        }
    }}
