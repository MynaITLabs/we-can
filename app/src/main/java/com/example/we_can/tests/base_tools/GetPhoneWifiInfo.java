package com.example.we_can.tests.base_tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.system.Os;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class GetPhoneWifiInfo {

    public GetPhoneWifiInfo() {
//        String s = this.GetDeviceInfo();
    }

    public static String GetDeviceInfo() {
        String device_model = Build.MANUFACTURER;
//        String device_model = Build.;
        Log.e("device model", device_model);
        return device_model;
    }

    @SuppressLint("MissingPermission")
    public static String GetWifiData(WifiManager wifi) {
        WifiInfo info = wifi.getConnectionInfo();
        String device_model = Build.MANUFACTURER;
        String b = Build.MODEL;

        //        String device_model = Build.;
        Log.e("device model", info.toString());
        b = Build.BRAND;

        Log.e("shivam model", getMacAddr());

        //        String device_model = Build.;
        Log.e("device model", b);
        b = Build.DEVICE;

        //        String device_model = Build.;
        Log.e("device model", b);
        b = Build.getRadioVersion();

        //        String device_model = Build.;
        Log.e("device model", b);


        return "";
    }

    public static String getMacAddr() {
        try {

            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface nif : all) {
                Log.e("gopi", nif.toString());
                Log.e("bahu", nif.toString());
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                Log.e("jasus", nif.toString());
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}
