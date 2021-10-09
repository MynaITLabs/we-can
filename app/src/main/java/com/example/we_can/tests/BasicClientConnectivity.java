package com.example.we_can.tests;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.we_can.tests.base_tools.GetPhoneWifiInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BasicClientConnectivity {


    public static int FOUR_WAY_HS_TIME;
    public static int GROUP_HS_TIME;
    public static int CX_TIME;
    private static ArrayList<String> cc_data;
    public static int CC_Status = 0;

    /***
     * Constructor
    ***/
    public static GetPhoneWifiInfo info;


    public BasicClientConnectivity(Context context, WifiManager wifi){
        this.StartTest(context, wifi);

    }
    
    public static void StartTest(Context context, WifiManager wifi){
        cc_data = new ArrayList<String>();
        CC_Status = 0;
        IntentFilter intentFilter = new IntentFilter();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                WifiInfo wifiInfo = wifi.getConnectionInfo();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Log.i("device status", timestamp.toString() + " " + wifiInfo.toString());
                cc_data.add(timestamp.toString() + " " + wifiInfo.toString());
                if (wifiInfo.getSupplicantState().toString() == "COMPLETED"){
                    CC_Status = 1;
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
    }



    public static void run_test(Context context, WifiManager wifiManager){

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            wifiManager.removeNetwork(i.networkId);
            System.out.println(i);
            //wifiManager.saveConfiguration();
        }
        wifiManager.setWifiEnabled(false);
        String networkSSID = "Pietronics-Guest";
        String networkPass = "12345678";

        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WifiNetworkSuggestion networkSuggestion1 =
                    new WifiNetworkSuggestion.Builder()
                            .setSsid(networkSSID)
                            .setWpa2Passphrase(networkPass)
                            .build();

            WifiNetworkSuggestion networkSuggestion2 =
                    new WifiNetworkSuggestion.Builder()
                            .setSsid(networkSSID)
                            .setWpa3Passphrase(networkPass)
                            .build();

            List<WifiNetworkSuggestion> suggestionsList = new ArrayList<>();
            suggestionsList.add(networkSuggestion1);
            suggestionsList.add(networkSuggestion2);

            wifiManager.addNetworkSuggestions(suggestionsList);
        }

        else{
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = String.format("\"%s\"", networkSSID);
            wifiConfiguration.preSharedKey = String.format("\"%s\"", networkPass);
            int wifiID = wifiManager.addNetwork(wifiConfiguration);
            wifiManager.removeNetwork(wifiID);
            wifiManager.enableNetwork(wifiID, true);
            Handler h = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    int i = CC_Status;
                    if (i == 1){
                        System.out.println("Test Completed");
                    }
                    System.out.println(i);
                    h.postDelayed(this, 100);
                }
            };
            r.run();
            h.postDelayed(r, 100);

//                    h.removeCallbacks(r);
        }



    }


}
