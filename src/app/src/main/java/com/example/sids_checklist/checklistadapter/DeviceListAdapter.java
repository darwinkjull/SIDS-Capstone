package com.example.sids_checklist.checklistadapter;

import android.bluetooth.BluetoothClass;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.R;
import com.example.sids_checklist.checklistmodel.DeviceModel;


import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private static ArrayList<DeviceModel> deviceList;

    private static OnItemClickListener onItemClickListener;

    public DeviceListAdapter(ArrayList<DeviceModel> deviceList, OnItemClickListener onItemClickListener){
        this.deviceList = deviceList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View deviceView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_card, parent, false);
        return new ViewHolder(deviceView);
    }

    public interface OnItemClickListener{
        void onItemClick(DeviceModel deviceModel);
    }
    public void onBindViewHolder(ViewHolder holder, int position){
        DeviceModel device = deviceList.get(position);
        holder.deviceName.setText(device.getDeviceName());
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public int getItemCount(){return deviceList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView deviceName;

        ViewHolder(View view) {
            super(view);
            deviceName = view.findViewById(R.id.profile_username);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(deviceList.get(getBindingAdapterPosition()));
            }
        }
    }
}
