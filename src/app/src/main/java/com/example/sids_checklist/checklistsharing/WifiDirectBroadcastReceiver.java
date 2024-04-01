package com.example.sids_checklist.checklistsharing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.sids_checklist.Sharing_Activity;

/**
 *  Implementation of this class was guided by the following page:
 *  https://developer.android.com/develop/connectivity/wifi/wifip2p
 */
public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private Sharing_Activity activity;

    public WifiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, Sharing_Activity activity){
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent){
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            if(WifiP2pManager.WIFI_P2P_STATE_ENABLED == intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)){
                Log.d("tag", "Wifi Enabled");
            }
            else{
                Log.d("tag", "Wifi Disabled");
            }
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
            if(manager != null){
                manager.requestPeers(channel, activity.peerListListener);
            }
        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){

        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){

        }
    }
}
