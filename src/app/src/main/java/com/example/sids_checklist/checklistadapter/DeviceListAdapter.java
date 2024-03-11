package com.example.sids_checklist.checklistadapter;

import android.bluetooth.BluetoothClass;
import android.content.Context;
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

    public DeviceListAdapter(List<String> deviceList){
        this.deviceList = deviceList;
    }

    @NonNull
    public DeviceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View deviceView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_card, parent, false);
        return new DeviceListAdapter.ViewHolder(deviceView);
    }

    public void onBindViewHolder(DeviceListAdapter.ViewHolder holder, int position){
        String deviceName = deviceList.get(position);

        holder.deviceName.setText(deviceName);
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
