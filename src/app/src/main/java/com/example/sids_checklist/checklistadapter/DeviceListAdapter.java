package com.example.sids_checklist.checklistadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.R;


import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private List<String> deviceList;

    private OnClickListener onClickListener;

    public DeviceListAdapter(List<String> deviceList){
        this.deviceList = deviceList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View deviceView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_card, parent, false);
        return new ViewHolder(deviceView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        String deviceName = deviceList.get(position);
        holder.deviceName.setText(deviceName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null){
                    onClickListener.onClick(deviceName);
                }
            }
        });

    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(String deviceName);
    }

    public int getItemCount(){return deviceList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;

        ViewHolder(View view) {
            super(view);
            deviceName = view.findViewById(R.id.profile_username);
        }
    }
}
