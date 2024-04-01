package com.example.sids_checklist.checklistsharing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

/**
 *  Implementation of this class was guided by the following page:
 *  https://developer.android.com/develop/connectivity/wifi/wifip2p
 */
public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    /**
     * setup the reciever for the broadcast of the data
     *
     */
    public WifiDirectBroadcastReceiver(){
        super();
    }

    /**
     * setup the behavior of the receiver depending on the action in the intent
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent){
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            // Implement these later
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){

        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){

        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){

        }
    }
}
