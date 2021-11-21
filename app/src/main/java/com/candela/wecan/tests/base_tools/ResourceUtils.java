package com.candela.wecan.tests.base_tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.HardwarePropertiesManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

import candela.lfresource.AndroidUI;
import candela.lfresource.StringKeyVal;

public class ResourceUtils extends AppCompatActivity implements AndroidUI{
    public static Context context;

    public ResourceUtils(Context context){
        this.context = context;
    }

    @Override
    public void setResourceInfo(int i, int i1) {

    }



    @SuppressLint({"HardwareIds", "MissingPermission"})
    @Override
    public Vector<StringKeyVal> requestPortUpdate(String s) {
        Vector<StringKeyVal> data_structure = new Vector<StringKeyVal>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        data_structure.add(new StringKeyVal("SSID",wifiManager.getConnectionInfo().getSSID().toString()));
        data_structure.add(new StringKeyVal("BSSID",wifiManager.getConnectionInfo().getBSSID().toString()));
        data_structure.add(new StringKeyVal("RSSI",String.valueOf(wifiManager.getConnectionInfo().getRssi())));
        data_structure.add(new StringKeyVal("Frequency",String.valueOf(wifiManager.getConnectionInfo().getFrequency())));
        data_structure.add(new StringKeyVal("Link speed",String.valueOf(wifiManager.getConnectionInfo().getLinkSpeed())));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            data_structure.add(new StringKeyVal("Tx Link speed",String.valueOf(wifiManager.getConnectionInfo().getTxLinkSpeedMbps())));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            data_structure.add(new StringKeyVal("Wi-Fi standard",String.valueOf(wifiManager.getConnectionInfo().getWifiStandard())));
            data_structure.add(new StringKeyVal("Max Supported Rx Link speed",String.valueOf(wifiManager.getConnectionInfo().getMaxSupportedRxLinkSpeedMbps())));
        }
        data_structure.add(new StringKeyVal("Max Supported Rx Link speed",String.valueOf(wifiManager.getConnectionInfo().getMacAddress())));

        System.out.println("kabutops: "  + data_structure);
        return data_structure;
    }
}
