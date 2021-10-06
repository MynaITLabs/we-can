package com.example.we_can.tests;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.we_can.tests.base_tools.GetPhoneWifiInfo;

import java.sql.Timestamp;

public class BasicClientConnectivity {


    public static int FOUR_WAY_HS_TIME;
    public static int GROUP_HS_TIME;
    public static int CX_TIME;


    /***
     * Constructor
    ***/
    public static GetPhoneWifiInfo info;


    public BasicClientConnectivity(Context context, WifiManager wifi){
        this.StartTest(context, wifi);

    }

    public static void StartTest(Context context, WifiManager wifi){
        IntentFilter intentFilter = new IntentFilter();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                WifiInfo wifiInfo = wifi.getConnectionInfo();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Log.i("device status", timestamp.toString() + " " + wifiInfo.toString());
                if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {

//                    Log.i("device status",  + SupplicantState.ASSOCIATED.toString());
                    if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
                        //do stuff
                    } else {
                        // wifi connection was lost
                    }
                }
            }
        };
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

}
