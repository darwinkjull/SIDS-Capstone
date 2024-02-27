package com.example.sids_checklist.checklistsharing;

import static android.os.Looper.getMainLooper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

/**
 * This class was developed following the guide at:
 * https://developer.android.com/develop/connectivity/wifi/wifip2p
 */

public class Sharing_P2PManager {
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver;

    public void OnCreate(Bundle savedInstanceState, Context context, Activity activity){
        manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(context, getMainLooper(), null);
        receiver = new WifiDirectBroadcastReceiver(manager, channel, activity);
    }
}
