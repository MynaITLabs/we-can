package com.candela.wecan.ui.home;

import static android.net.wifi.WifiConfiguration.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import com.candela.wecan.R;
import com.candela.wecan.databinding.FragmentHomeBinding;
import com.candela.wecan.tests.base_tools.CardUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import candela.lfresource.StringKeyVal;

public class HomeFragment extends Fragment {
    public static Context context;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button refresh_button;
    private static final String FILE_NAME = "data.conf";
    private TextView ip_show;
    public String ip, ip1;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button button = root.findViewById(R.id.update_lf);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardUtils cardUtils = new CardUtils(getContext());
            }
        });

//        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onChanged(@Nullable String s) {
                Map <String,String> data =  new HashMap<String,String>();
                data.put("MANUFACTURER", Build.MANUFACTURER);
                data.put("MODEL", Build.MODEL);
                data.put("PRODUCT", Build.PRODUCT);
                data.put("RELEASE", Build.VERSION.RELEASE);
                data.put("INCREMENTAL", Build.VERSION.INCREMENTAL);
                data.put("SDK No.", String.valueOf(Build.VERSION.SDK_INT));
                data.put("BOARD", Build.BOARD);
                data.put("BRAND", Build.BRAND);
                data.put("CPU_ABI", Build.CPU_ABI);
                data.put("HARDWARE", Build.HARDWARE);
                data.put("HOST", Build.HOST);
                data.put("ID", Build.ID);

                long availMem  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long totalMem  = Runtime.getRuntime().totalMemory();
                Vector<StringKeyVal> wifi_capabilities = new Vector<StringKeyVal>();
                Vector<StringKeyVal> wifi_mode = new Vector<StringKeyVal>();
                Vector<StringKeyVal> wifi_encryption = new Vector<StringKeyVal>();

                WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo();
                List<ScanResult> networkList = wifiManager.getScanResults();
//                networkList info [SSID: Candela-Office, BSSID: 00:31:92:c0:67:be, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS][WPS], level: -49, frequency: 2427, timestamp: 686867706546, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 1, centerFreq0: 2437, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: CT-Living Space, BSSID: b0:39:56:1c:3a:b6, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS][WPS], level: -54, frequency: 2422, timestamp: 686867706568, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 2422, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: DIRECT-4ZF5-E4200series, BSSID: 36:9f:7b:0b:68:f5, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS][WPS], level: -63, frequency: 2427, timestamp: 686867706587, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 2427, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: , BSSID: 06:31:92:c0:7c:8e, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS], level: -65, frequency: 2427, timestamp: 686867706594, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 1, centerFreq0: 2437, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: Candela-Office, BSSID: 00:31:92:c0:7c:8e, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS][WPS], level: -66, frequency: 2427, timestamp: 686867706612, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 1, centerFreq0: 2437, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: Candela-QA, BSSID: dc:ef:09:e3:b8:7b, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS][WPS], level: -74, frequency: 2452, timestamp: 686867706619, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 2452, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: Maverick, BSSID: 00:03:7f:12:cf:ce, capabilities: [ESS], level: -75, frequency: 2412, timestamp: 686867706627, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 2412, centerFreq1: 0, standard: 11ax, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: Apple-2.4G, BSSID: b4:f9:49:17:e1:45, capabilities: [WPA-PSK-TKIP][WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS], level: -77, frequency: 2447, timestamp: 686867706642, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 2447, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: 062samo, BSSID: 70:5d:cc:d4:ce:0e, capabilities: [ESS][WPS], level: -77, frequency: 2437, timestamp: 686867706634, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 1, centerFreq0: 2447, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: Router2, BSSID: 78:d2:94:4a:aa:eb, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS][WPS], level: -87, frequency: 2437, timestamp: 686867706649, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 2437, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null, SSID: Candela-Office, BSSID: 00:31:92:c0:7c:3e, capabilities: [WPA2-PSK-CCMP][RSN-PSK-CCMP][ESS][WPS], level: -88, frequency: 2427, timestamp: 686867706658, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 1, centerFreq0: 2437, centerFreq1: 0, standard: 11n, 80211mcResponder: is not supported, Radio Chain Infos: null]
                ScanResult sss = networkList.get(0);
                System.out.println("Capabilities000 " + sss);
                System.out.println("networkList " + networkList);
                Boolean AC_11 = null;
                Boolean AX_11 = null;
                Boolean N_11 = null;
                Boolean legacy = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    AC_11 = wifiManager.isWifiStandardSupported(ScanResult.WIFI_STANDARD_11AC);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    AX_11 = wifiManager.isWifiStandardSupported(ScanResult.WIFI_STANDARD_11AX);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    N_11 = wifiManager.isWifiStandardSupported(ScanResult.WIFI_STANDARD_11N);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    legacy = wifiManager.isWifiStandardSupported(ScanResult.WIFI_STANDARD_LEGACY);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    wifi_capabilities.add(new StringKeyVal("5G", String.valueOf((wifiManager.is5GHzBandSupported()))));
                }else {
                    wifi_capabilities.add(new StringKeyVal("5G", String.valueOf(true)));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    wifi_capabilities.add(new StringKeyVal("6G", String.valueOf((wifiManager.is6GHzBandSupported()))));
                }else {
                    wifi_capabilities.add(new StringKeyVal("6G", String.valueOf(true)));
                }

                if (Build.VERSION.SDK_INT >= 31) {
                    // This was added in API 31, I guess before then 2.4 was always supported.
                    wifi_capabilities.add(new StringKeyVal("2G", String.valueOf((wifiManager.is24GHzBandSupported())))); // This line gives an error
                }
                else {
                    wifi_capabilities.add(new StringKeyVal("2G", String.valueOf(true)));
                }

                wifi_mode.add(new StringKeyVal("11-AC", String.valueOf(AC_11)));
                wifi_mode.add(new StringKeyVal("11-AX", String.valueOf(AX_11)));
                wifi_mode.add(new StringKeyVal("11-N", String.valueOf(N_11)));
//                wifi_mode.add(new StringKeyVal("LEGACY", String.valueOf(legacy)));

//        WIFI-ENCRYPTION
                Boolean wpa3sea = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    wpa3sea = wifiManager.isWpa3SaeSupported();
                }
                Boolean Wpa3SuiteB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Wpa3SuiteB = wifiManager.isWpa3SuiteBSupported();
                }
                Boolean passpoint = wifiManager.isP2pSupported();
                wifi_encryption.add(new StringKeyVal("wpa3S", String.valueOf(wpa3sea)));
                wifi_encryption.add(new StringKeyVal("wpa3SB", String.valueOf(Wpa3SuiteB)));
                wifi_encryption.add(new StringKeyVal("P.P", String.valueOf(passpoint)));

                if (Build.VERSION.SDK_INT >= 31){
                    wifi_encryption.add(new StringKeyVal("wpa3SH2e", String.valueOf(true)));
                }
                else {
                    wifi_encryption.add(new StringKeyVal("wpa3SaeH2e", String.valueOf(true)));
                }

                ip_show = getView().findViewById(R.id.server_ip_info);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                Map<String,?> keys = sharedPreferences.getAll();
                String current_ip = (String) keys.get("current-ip");
                String current_resource = (String) keys.get("current-resource");
                String current_realm = (String) keys.get("current-realm");
                String password = (String) keys.get("current-passwd");
                String username = (String) keys.get("user_name");

                ip_show.setText("USERNAME: " + username + "\nSERVER IP: " + current_ip + "\nREALM: " + current_realm + "\nCARD: " + current_resource);
//                server_ip.setText(last_ip);
                data.put("WiFi CAPABILITY", String.valueOf(wifi_capabilities));
                data.put("WiFi ENCRYPTION", String.valueOf(wifi_encryption));
                data.put("SSID", info.getSSID());
                data.put("SSID PASSWORD", password);
                data.put("WiFi MODE", String.valueOf(wifi_mode));
                data.put("USERNAME", username);

                final TableLayout table = (TableLayout) getView().findViewById(R.id.table);
                table.setPadding(10, 0, 10,0);
                TableRow heading = new TableRow(getActivity());
                heading.setBackgroundColor(Color.rgb(120, 156,175));
                TextView sl_head = new TextView(getActivity());
                sl_head.setText(" SL. ");
                sl_head.setTextColor(Color.BLACK);
                sl_head.setGravity(Gravity.LEFT);
                heading.addView(sl_head);
                TextView key_head = new TextView(getActivity());
                key_head.setText(" KEY ");
                key_head.setTextColor(Color.BLACK);
                key_head.setGravity(Gravity.LEFT);
                heading.addView(key_head);
                TextView val_head = new TextView(getActivity());
                val_head.setText(" VALUE ");
                val_head.setTextColor(Color.BLACK);
                val_head.setGravity(Gravity.LEFT);
                heading.addView(val_head);
                table.addView(heading);

                int i = 1;
                for (Map.Entry<String,String> entry : data.entrySet() ) {
                    TableRow tbrow = new TableRow(getActivity());
                    if (i%2 == 0){
                        tbrow.setBackgroundColor(Color.rgb(211,211,211));
                    }else {
                        tbrow.setBackgroundColor(Color.rgb(192,192,192));
                    }

                    TextView sl_view = new TextView(getActivity());
                    sl_view.setText(String.valueOf(i));
                    sl_view.setTextSize(12);
                    sl_view.setTextColor(Color.BLACK);
                    sl_view.setGravity(Gravity.LEFT);
                    tbrow.addView(sl_view);
                    TextView key_view = new TextView(getActivity());
                    key_view.setText(entry.getKey());
                    key_view.setTextSize(12);
                    key_view.setTextColor(Color.BLACK);
                    key_view.setGravity(Gravity.LEFT);
                    tbrow.addView(key_view);
                    TextView val_view = new TextView(getActivity());
                    val_view.setText(entry.getValue());
                    val_view.setTextSize(12);
                    val_view.setTextColor(Color.BLACK);
                    val_view.setGravity(Gravity.LEFT);
                    tbrow.addView(val_view);
                    table.addView(tbrow);
                    i= i+1;
                }

//                String data = "Manufacturer: " + manufacturer + "\n" + "Model: " + model
//                        + "\n" + "Product: " + product + "\n" + "Username: " + username + "\n" + "Release: "
//                        + release +  "\n" + "version_incremental: " + version_incremental + "\n" +
//                        "Version_sdk_number: " + version_sdk_number + "\n" + "Board: " + board + "\n" +
//                        "Brand: " + brand + "\n" + "CPU_abi: " + cpu_abi + "\n" +
//                        "Hardware: " + hardware + "\n" + "Host: " + host + "\n" + "ID: " + id + "\n" +
//                        "AvailMem: " + availMem + "\n" + "TotalMem: " + totalMem + "\n" + "Wi-Fi Capabilities: "
//                        + wifi_capabilities + "\n" + "Wi-Fi Encryption: " + wifi_encryption + "\n"
//                        + "WifiInfo: " + info.getSSID() + "\n" + "Password: " + password + "\n";
                }
        });
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}