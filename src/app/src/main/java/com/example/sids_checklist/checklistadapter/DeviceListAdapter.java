package com.example.sids_checklist.checklistadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.R;


import java.util.List;
/**
 * The DeviceListAdapter class is responsible for managing the list of devices in a RecyclerView.
 */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private final List<String> deviceList;

    private OnClickListener onClickListener;

    /**
     * Constructs a DeviceListAdapter with the given list of devices.
     *
     * @param deviceList The list of device names to be displayed.
     */
    public DeviceListAdapter(List<String> deviceList){
        this.deviceList = deviceList;
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param position The view type of the new View.
     * @return Creates a new ViewHolder by inflating the layout from device_card.xml.
     */
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View deviceView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_card, parent, false);
        return new ViewHolder(deviceView);
    }

    /**
     * Binds data to the ViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(ViewHolder holder, int position){
        String deviceName = deviceList.get(position);
        holder.deviceName.setText(deviceName);

        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null){
                onClickListener.onClick(deviceName);
            }
        });

    }

    /**
     * Sets a custom OnClickListener for the RecyclerView items.
     * @param onClickListener the event listener
     */
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }


    /**
     * the interface for the OnClickListener to monitor user inputs
     */
    public interface OnClickListener{
        /**
         * Called when a device item is clicked.
         *
         * @param deviceName The name of the clicked device.
         */
        void onClick(String deviceName);
    }

    /**
     * @return Returns the total number of items in the list.
     */
    public int getItemCount(){return deviceList.size();}

    /**
     * ViewHolder class for caching view components of item layout.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;

        ViewHolder(View view) {
            super(view);
            deviceName = view.findViewById(R.id.profile_username);
        }
    }
}
