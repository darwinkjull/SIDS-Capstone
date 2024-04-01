package com.example.sids_checklist;

import android.annotation.SuppressLint;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.checklistadapter.DeviceListAdapter;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Sharing_SendInfo class manages the UI for sending profile information to nearby devices via Wi-Fi Direct (P2P).
 * It displays a popup window with a list of available devices to send the profile information to.
 * Implements WifiP2pManager.PeerListListener to receive updates on available peers.
 */
public class Sharing_SendInfo implements WifiP2pManager.PeerListListener {

    private List<String> deviceList;
    private DeviceListAdapter deviceListAdapter;
    private RecyclerView deviceRecyclerList;
    private String selectedDeviceName;

    /**
     * Displays the send profile popup window.
     *
     * @param view The view from which the popup window is launched.
     * @param manager The WifiP2pManager instance.
     * @param channel The WifiP2pManager.Channel instance.
     * @param peerListListener The listener to receive updates on available peers.
     * @param selectedProfiles The list of selected profiles to share.
     */
    public void showSendProfilePopUp(View view, WifiP2pManager manager, WifiP2pManager.Channel channel, WifiP2pManager.PeerListListener peerListListener, List<String> selectedProfiles) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View sendInfoPopUpView = inflater.inflate(R.layout.sharing_send, null);
        PopupWindow popupWindow = new PopupWindow(sendInfoPopUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button acceptInfoFalseButton = sendInfoPopUpView.findViewById(R.id.cancelOperation);

        ProgressBar sharingProgressBar = sendInfoPopUpView.findViewById(R.id.sharingProgressBar);
        sharingProgressBar.setVisibility(View.VISIBLE);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(view.getContext());
        profile_db.openDatabase();
        deviceList = new ArrayList<>();
        deviceList.add("Test Device");

        deviceRecyclerList = sendInfoPopUpView.findViewById(R.id.profileSelectionList);
        deviceListAdapter = new DeviceListAdapter(deviceList);
        deviceRecyclerList.setLayoutManager(new LinearLayoutManager(deviceRecyclerList.getContext()));
        deviceRecyclerList.setAdapter(deviceListAdapter);

        deviceListAdapter.setOnClickListener(deviceName -> {
            selectedDeviceName = deviceName;
            Log.d("tag", "Selected device: " + selectedDeviceName);
        });

        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                manager.requestPeers(channel, peerListListener);
                Log.d("tag", "Detected peers!");

            }

            @Override
            public void onFailure(int reason) {
                Log.d("tag", "Failed to detect peers");

            }
        });

        acceptInfoFalseButton.setOnClickListener(v -> popupWindow.dismiss());
    }

    /**
     * Called when the list of available peers is updated.
     *
     * @param peerList The updated list of available peers.
     */
    @Override
    public void onPeersAvailable (WifiP2pDeviceList peerList) {
        for (WifiP2pDevice device : peerList.getDeviceList()) {
            deviceList.add(device.deviceName);
        }
        deviceListAdapter = new DeviceListAdapter(deviceList);
        deviceRecyclerList.setLayoutManager(new LinearLayoutManager(deviceRecyclerList.getContext()));
        deviceRecyclerList.setAdapter(deviceListAdapter);

        Log.d("tag", "Peer list created and displayed");


    }
}
