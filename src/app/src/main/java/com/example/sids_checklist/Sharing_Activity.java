package com.example.sids_checklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sids_checklist.checklistadapter.ProfileListCheckableAdapter;
import com.example.sids_checklist.checklistmodel.ProfileModel;
import com.example.sids_checklist.checklistsharing.WifiDirectBroadcastReceiver;
import com.example.sids_checklist.checklistutils.Profile_DatabaseHandler;

import java.util.List;
import java.util.Objects;


/**
 *  To share files using wifi direct, we first need to define our wifi direct methods, classes etc.
 *  to get the sharing backend operational. Following this, we will convert our tables into JSON,
 *  send via wifi direct, then unpackage them and merge into the existing tables.
 *  (https://developer.android.com/develop/connectivity/wifi/wifip2p)
 *  (https://stackoverflow.com/questions/38748855/can-i-use-androids-wifi-p2p-api-to-transfer-sqlite-data-between-apps)
 *  (https://stackoverflow.com/questions/25722585/convert-sqlite-to-json)
 */
public class Sharing_Activity extends AppCompatActivity {
    private int profileID;
    private ProfileListCheckableAdapter profileListCheckableAdapter;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;
    private WifiP2pManager.PeerListListener peerListListener;
    private List<String> selectedProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        profileID = getIntent().getIntExtra("profile_id", -1);

        Profile_DatabaseHandler profile_db = new Profile_DatabaseHandler(this);
        profile_db.openDatabase();

        Button returnFromSharingButton = findViewById(R.id.returnFromSharingButton);
        Button sendInfoButton = findViewById(R.id.sendInfoButton);
        RecyclerView profileRecyclerList = findViewById(R.id.profilesSharingList);

        List<ProfileModel> profileModelList = profile_db.getAllProfiles();
        profileListCheckableAdapter = new ProfileListCheckableAdapter(profile_db, this, profileModelList);
        profileRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        profileRecyclerList.setAdapter(profileListCheckableAdapter);

        returnFromSharingButton.setOnClickListener(v -> {
            Intent i = new Intent(Sharing_Activity.this, Main_Activity.class);
            i.putExtra("profile_id", profileID);
            startActivity(i);
        });

        sendInfoButton.setOnClickListener(v -> {
            selectedProfiles = profileListCheckableAdapter.getCheckedProfiles();
            if(!selectedProfiles.isEmpty()) {
                Log.d("tag", "Starting sendInfoPopup, selected profiles: " + selectedProfiles);
                Sharing_SendInfo sendInfoPopup = new Sharing_SendInfo();
                sendInfoPopup.showSendProfilePopUp(v, manager, channel, peerListListener, selectedProfiles);
            }
        });

        // P2P sharing below:
        manager = (WifiP2pManager) this.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WifiDirectBroadcastReceiver();

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

    /**
     * Registers the Wi-Fi P2P broadcast receiver when the activity resumes.
     */
    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    /**
     * Unregisters the Wi-Fi P2P broadcast receiver when the activity pauses.
     */
    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(receiver);
    }

}
