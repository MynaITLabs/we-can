package com.example.we_can.tests.base_tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WifiReceiver extends BroadcastReceiver {
    Spinner spinner;
    WifiManager wifiManager;
    StringBuilder sb;
    ListView wifiDeviceList;
    public List<ScanResult> wifiList;


    public WifiReceiver(WifiManager wifiManager, ListView wifiDeviceList, Spinner spinner) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
        this.spinner = spinner;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            sb = new StringBuilder();
            wifiList = wifiManager.getScanResults();
            ArrayList<String> deviceList = new ArrayList<>();
            ArrayList<String> ssidList = new ArrayList<>();
            for (ScanResult scanResult : wifiList) {
                sb.append("\n").append(scanResult.SSID).append(" - ").append(scanResult.capabilities);
                deviceList.add(scanResult.SSID + " - " + scanResult.capabilities);
                ssidList.add(scanResult.SSID);
                System.out.println(scanResult.capabilities);
                System.out.println(scanResult.SSID);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ssidList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }


}