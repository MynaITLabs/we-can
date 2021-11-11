package com.candela.wecan.tests.base_tools;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;

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


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Vector<StringKeyVal> requestPortUpdate(String s) {
        Vector<StringKeyVal> data_structure = new Vector<StringKeyVal>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        data_structure.add(new StringKeyVal("SSID",wifiManager.getConnectionInfo().getSSID().toString()));
        data_structure.add(new StringKeyVal("BSSID",wifiManager.getConnectionInfo().getBSSID().toString()));
        data_structure.add(new StringKeyVal("RSSI",String.valueOf(wifiManager.getConnectionInfo().getRssi())));
        data_structure.add(new StringKeyVal("Frequency",String.valueOf(wifiManager.getConnectionInfo().getFrequency())));
        data_structure.add(new StringKeyVal("Link speed",String.valueOf(wifiManager.getConnectionInfo().getLinkSpeed())));
        data_structure.add(new StringKeyVal("Tx Link speed",String.valueOf(wifiManager.getConnectionInfo().getTxLinkSpeedMbps())));
//        data_structure.add(new StringKeyVal("Link speed",String.valueOf(wifiManager.getConnectionInfo().getLinkSpeed())));


        return data_structure;
    }
}
