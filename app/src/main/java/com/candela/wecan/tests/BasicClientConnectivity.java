package com.candela.wecan.tests;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.candela.wecan.tests.base_tools.GetPhoneWifiInfo;
import com.candela.wecan.tests.base_tools.HTTPHandler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BasicClientConnectivity {


    public static int FOUR_WAY_HS_TIME;
    public static int GROUP_HS_TIME;
    public static int CX_TIME;
    public static ArrayList<String> cc_data;
    public static int CC_Status = 0;
    public static WifiManager wifi;
    /***
     * Constructor
    ***/
    public static GetPhoneWifiInfo info;
    public static IntentFilter intentFilter;
    public static BroadcastReceiver broadcastReceiver;
    public static Context context;

    public BasicClientConnectivity(Context context, WifiManager wifi){
        this.wifi = wifi;
        this.context = context;
        this.intentFilter = new IntentFilter();
        this.cc_data = new ArrayList<String>();
    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    public static void run_test(Context context, WifiManager wifiManager, String wifi_name, String pass, HTTPHandler httpHandler) {


        CC_Status = 0;

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                WifiInfo wifiInfo = wifi.getConnectionInfo();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Log.i("device status", timestamp.toString() + " " + wifiInfo.toString());
                cc_data.add(timestamp.toString() + " " + wifiInfo.toString());
                System.out.println(cc_data);
                if (wifiInfo.getSupplicantState().toString() == "COMPLETED" && wifi_name == wifiInfo.getSSID()){
                    CC_Status = 1;
                    context.unregisterReceiver(this);
                }

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


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }

        wifiManager.setWifiEnabled(false);
        String networkSSID = wifi_name;
        String networkPass = pass;
        Log.e("ssid", wifi_name);
        Log.e("pass", pass);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration conf : configuredNetworks){
            if (conf.networkId == connectionInfo.getNetworkId()){
                wifiManager.disableNetwork(conf.networkId);
                wifiManager.removeNetwork(conf.networkId);
                break;
            }
        }
        WifiConfiguration wifiConfiguration = new WifiConfiguration();

        wifiConfiguration.SSID = String.format("\"%s\"", networkSSID);
        wifiConfiguration.preSharedKey = String.format("\"%s\"", networkPass);
        Log.e("log", wifiConfiguration.toString());
        int wifiID = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.disableNetwork(wifiID);
        wifiManager.removeNetwork(wifiID);
        wifiManager.setWifiEnabled(true);
        wifiID = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.enableNetwork(wifiID, true);


    }


}
