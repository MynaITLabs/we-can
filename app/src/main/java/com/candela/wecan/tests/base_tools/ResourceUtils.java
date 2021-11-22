package com.candela.wecan.tests.base_tools;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.HardwarePropertiesManager;
import android.os.Parcel;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import candela.lfresource.AndroidUI;
import candela.lfresource.PlatformInfo;
import candela.lfresource.StringKeyVal;
import candela.lfresource.PlatformInfo;

public class ResourceUtils extends AppCompatActivity implements AndroidUI{
    public static Context context;

    public ResourceUtils(Context context){
        this.context = context;
    }

    @Override
    public void setResourceInfo(int i, int i1) {
       // TODO:  Store this info for next time.
    }

    @SuppressLint({"HardwareIds", "MissingPermission", "NewApi"})
    @Override
    public Vector<StringKeyVal> requestPortUpdate(String s) {
        Vector<StringKeyVal> data_structure = new Vector<StringKeyVal>();
        if (s.equals("wlan0")){
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
            data_structure.add(new StringKeyVal("DHCP-IPv4",String.valueOf(wifiManager.getDhcpInfo().ipAddress)));
            data_structure.add(new StringKeyVal("DHCP-Gateway",String.valueOf(wifiManager.getDhcpInfo().gateway)));
            data_structure.add(new StringKeyVal("DHCP-DNS1",String.valueOf(wifiManager.getDhcpInfo().dns1)));
            data_structure.add(new StringKeyVal("DHCP-DNS2",String.valueOf(wifiManager.getDhcpInfo().dns2)));
            data_structure.add(new StringKeyVal("DHCP-Lease-Duration",String.valueOf(wifiManager.getDhcpInfo().leaseDuration)));
            data_structure.add(new StringKeyVal("DHCP-Server",String.valueOf(wifiManager.getDhcpInfo().serverAddress)));

            System.out.println(data_structure);
            return data_structure;
        }
        if (s.equals("wiphy0")){
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<NetworkInterface> all = null;
            try {
                all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (nif.getName().equals("wlan0")){
                        System.out.println(nif.getName());
                        System.out.println(nif.getInetAddresses().toString());
                        data_structure.add(new StringKeyVal("Hardware-Address",Base64.getEncoder().encodeToString(nif.getHardwareAddress())));
                        data_structure.add(new StringKeyVal("MTU", String.valueOf(nif.getMTU())));
                        data_structure.add(new StringKeyVal("is-P2P", String.valueOf(nif.isPointToPoint())));
                        data_structure.add(new StringKeyVal("Supports-Multicast", String.valueOf(nif.supportsMulticast())));
                        data_structure.add(new StringKeyVal("Up", String.valueOf(nif.isUp())));
                        data_structure.add(new StringKeyVal("Hardware", String.valueOf(Build.HARDWARE)));

                    }

                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

            return data_structure;
        }
        else if (s.equals("rmnet_data1")){
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            data_structure.add(new StringKeyVal("Network-Operator",String.valueOf(telephonyManager.getNetworkOperatorName())));
            List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
            String strength = "";
            if(cellInfos!=null){
                for (int i = 0 ; i<cellInfos.size(); i++){
                    if (cellInfos.get(i).isRegistered()){
                        if(cellInfos.get(i) instanceof CellInfoWcdma){
                            CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                            strength = String.valueOf(cellSignalStrengthWcdma.getDbm());
                        }else if(cellInfos.get(i) instanceof CellInfoGsm){
                            CellInfoGsm cellInfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthGsm cellSignalStrengthGsm = cellInfogsm.getCellSignalStrength();
                            strength = String.valueOf(cellSignalStrengthGsm.getDbm());
                        }else if(cellInfos.get(i) instanceof CellInfoLte){
                            CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                            strength = String.valueOf(cellSignalStrengthLte.getDbm());
                        }
                    }
                }
                data_structure.add(new StringKeyVal("Signal-Strength",String.valueOf(strength)));
            }
            return data_structure;
        }
        else{
            return data_structure;
        }

    }


    public PlatformInfo requestPlatformUpdate() {
       PlatformInfo pi = new PlatformInfo();

       // TODO:  Fix me, PlatformInfo, Build and WifiManager objects provide useful info.

       pi.manufacturer = "samsung";
       pi.model = "a11";
       pi.wifi_capabilities = new Vector<>();
       pi.username = "";
       return pi;
    }
}
