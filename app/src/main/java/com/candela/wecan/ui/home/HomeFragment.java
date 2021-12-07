package com.candela.wecan.ui.home;

import static android.net.wifi.WifiConfiguration.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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
import android.widget.Toast;

import com.candela.wecan.R;
import com.candela.wecan.databinding.FragmentHomeBinding;
import com.candela.wecan.tests.base_tools.CardUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import candela.lfresource.StringKeyVal;

public class HomeFragment extends Fragment {
    public static Context context;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static final String FILE_NAME = "data.conf";
    private TextView ip_show;
    public Boolean live_table_flag = false, flag;
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
            @SuppressLint({"MissingPermission", "NewApi"})
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onChanged(@Nullable String s) {
//              FOR TESTING SDK VERSION FOR DIFFRENT PHONES
                if (Build.VERSION.SDK_INT <= 29){
                    Toast toast;
                    toast = Toast.makeText(getContext(), "Your phone's SDK is less than 30.", Toast.LENGTH_SHORT);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.RED);
                    toast.show();
                }else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), "Your Phone's SDK IS OK", Toast.LENGTH_LONG);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.GREEN);
                    toast.show();
                }
//                BUTTONS FOR ACTIONS TO BE DONE ONCLICK...
                Button wifi_info_btn, system_info_btn, rxtx_btn;
                wifi_info_btn = getActivity().findViewById(R.id.wifi_info_btn);
                system_info_btn = getActivity().findViewById(R.id.system_info_btn);
                rxtx_btn = getActivity().findViewById(R.id.rxtx_btn);
//                SWITCH BUTTON TO SAVE DATA....
                Switch switch_btn;
                switch_btn = getActivity().findViewById(R.id.save_data_switch);
//                Boolean switchState = switch_btn.isChecked();
//                TABLE LAYOUT FOR SHOWING TABLE DATA...
                final TableLayout wifi_table = (TableLayout) getView().findViewById(R.id.table);
                final TableLayout sys_table = (TableLayout) getView().findViewById(R.id.table);
                final TableLayout live_table = (TableLayout) getView().findViewById(R.id.table);

                ip_show = getView().findViewById(R.id.server_ip_info);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                Map<String,?> keys = sharedPreferences.getAll();
                String username = (String) keys.get("user_name");
                ip_show = getView().findViewById(R.id.server_ip_info);
                String current_ip = (String) keys.get("current-ip");
                String current_resource = (String) keys.get("current-resource");
                String current_realm = (String) keys.get("current-realm");
//                ip_show.setText("USERNAME: " + username + "\nSERVER IP: " + current_ip);
                ip_show.setText("User-Name: " + username + "\nServer: " + current_ip + "\nRealm: " + current_realm + "\nResource: " + current_resource);

                switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        flag = switch_btn.isChecked();
                            Handler handler = new Handler();
                            final Runnable save_data = new Runnable() {
                                @Override
                                public void run() {
//                                    Data Saving in csv format
                                    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                                    String fileName = "Live_Data.csv";
                                    String filePath = baseDir + File.separator + fileName;
                                    System.out.println("filePath: "+filePath);
                                    File file = new File(filePath);
                                    if (!file.exists()) {
                                        try {
                                            file.createNewFile();
                                        } catch (IOException ioe) {
                                            ioe.printStackTrace();
                                        }
                                    }
                                    try {
                                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                                        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
                                        writer.append("data,data,data,data,data,data,data,data,data");
                                        writer.close();
                                        fileOutputStream.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (flag){
                                        handler.postDelayed(this, 1000);
                                    }else{
                                        handler.removeCallbacks(this);
                                    }
                                }
                            };
                            handler.post(save_data);
                    }
                });


                wifi_info_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        wifi_info_btn.setBackgroundColor(Color.rgb(255,211,211));
                        sys_table.removeAllViews();
                        live_table_flag = false;
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                        Map<String,?> keys = sharedPreferences.getAll();
                        String password = (String) keys.get("current-passwd");

//                        Map <String,String> system_info =  new HashMap<String,String>();
                        Map <String,String> wifi_info =  new HashMap<String,String>();
                        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiinfo = wifiManager.getConnectionInfo();

                        wifi_info.put("SSID", wifiinfo.getSSID());
                        wifi_info.put("MAC", wifiinfo.getMacAddress());
                        wifi_info.put("BSSID", wifiinfo.getBSSID());
                        wifi_info.put("State",  String.valueOf(wifiinfo.getSupplicantState()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                           wifi_info.put("Standard ", String.valueOf(wifiinfo.getWifiStandard())); // Getting error for few phones
                        }
                        wifi_info.put("LinkSpeed", String.valueOf(wifiinfo.getLinkSpeed()));
                        wifi_info.put("frequency", String.valueOf(wifiinfo.getFrequency()));
                        wifi_info.put("PASSWORD", password);
                        System.out.println("wifi_info"+wifi_info);

                        wifi_table.setPadding(10, 0, 10,0);
                        TableRow heading = new TableRow(getActivity());
                        heading.setBackgroundColor(Color.rgb(120, 156,175));
                        TextView sl_head = new TextView(getActivity());
                        sl_head.setText(" SL. ");
                        sl_head.setTextColor(Color.BLACK);
                        sl_head.setGravity(Gravity.CENTER);
                        heading.addView(sl_head);
                        TextView key_head = new TextView(getActivity());
                        key_head.setText(" KEY ");
                        key_head.setTextColor(Color.BLACK);
                        key_head.setGravity(Gravity.CENTER);
                        heading.addView(key_head);
                        TextView val_head = new TextView(getActivity());
                        val_head.setText(" VALUE ");
                        val_head.setTextColor(Color.BLACK);
                        val_head.setGravity(Gravity.CENTER);
                        heading.addView(val_head);
                        wifi_table.addView(heading);

                        int i = 1;
                        for (Map.Entry<String,String> entry : wifi_info.entrySet() ) {
                            TableRow tbrow = new TableRow(getActivity());
                            if (i%2 == 0){
                                tbrow.setBackgroundColor(Color.rgb(211,211,211));
                            }else {
                                tbrow.setBackgroundColor(Color.rgb(192,192,192));
                            }

                            TextView sl_view = new TextView(getActivity());
                            sl_view.setText(String.valueOf(i) + ".");
                            sl_view.setTextSize(12);
                            sl_view.setTextColor(Color.BLACK);
                            sl_view.setGravity(Gravity.CENTER);
                            tbrow.addView(sl_view);
                            TextView key_view = new TextView(getActivity());
                            key_view.setText(entry.getKey());
                            key_view.setTextSize(12);
                            key_view.setTextColor(Color.BLACK);
                            key_view.setGravity(Gravity.CENTER);
                            tbrow.addView(key_view);
                            TextView val_view = new TextView(getActivity());
                            val_view.setText(entry.getValue());
                            val_view.setTextSize(12);
                            val_view.setTextColor(Color.BLACK);
                            val_view.setGravity(Gravity.CENTER);
                            tbrow.addView(val_view);
                            wifi_table.addView(tbrow);
                            i= i+1;
                        }
                    }
                });

                system_info_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        system_info_btn.setBackgroundColor(Color.rgb(255,211,211));
                        wifi_table.removeAllViews();
                        live_table_flag = false;
                        Vector<StringKeyVal> wifi_capabilities = new Vector<StringKeyVal>();
                        Vector<StringKeyVal> wifi_mode = new Vector<StringKeyVal>();
                        Vector<StringKeyVal> wifi_encryption = new Vector<StringKeyVal>();

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                        Map<String,?> keys = sharedPreferences.getAll();
                        String password = (String) keys.get("current-passwd");

                        Map <String,String> system_info =  new HashMap<String,String>();
                        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiinfo = wifiManager.getConnectionInfo();



                        system_info.put("MANUFACTURER", Build.MANUFACTURER);
                        system_info.put("MODEL", Build.MODEL);
                        system_info.put("PRODUCT", Build.PRODUCT);
                        system_info.put("RELEASE", Build.VERSION.RELEASE);
                        system_info.put("INCREMENTAL", Build.VERSION.INCREMENTAL);
                        system_info.put("SDK No.", String.valueOf(Build.VERSION.SDK_INT));
                        system_info.put("BOARD", Build.BOARD);
                        system_info.put("BRAND", Build.BRAND);
                        system_info.put("CPU_ABI", Build.CPU_ABI);
                        system_info.put("HARDWARE", Build.HARDWARE);
                        system_info.put("HOST", Build.HOST);
                        system_info.put("ID", Build.ID);
                        system_info.put("PHONE IP", Formatter.formatIpAddress(wifiinfo.getIpAddress()));

                        Boolean AC_11 = null;
                        Boolean AX_11 = null;
                        Boolean N_11 = null;
                        Boolean legacy = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            AC_11 = wifiManager.isWifiStandardSupported(ScanResult
                                    .WIFI_STANDARD_11AC);
                        }else {
                            AC_11 = false;
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            AX_11 = wifiManager.isWifiStandardSupported(ScanResult
                                    .WIFI_STANDARD_11AX);
                        }else {
                            AX_11 = false;
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            N_11 = wifiManager.isWifiStandardSupported(ScanResult
                                    .WIFI_STANDARD_11N);
                        }else {
                            N_11 = false;
                        }


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            legacy = wifiManager.isWifiStandardSupported(ScanResult
                                    .WIFI_STANDARD_LEGACY);
                        }else {
                            legacy = false;
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
                        wifi_mode.add(new StringKeyVal("LEGACY", String.valueOf(legacy)));

                        sys_table.setPadding(10, 0, 10,0);
                        TableRow heading = new TableRow(getActivity());
                        heading.setBackgroundColor(Color.rgb(120, 156,175));
                        TextView sl_head = new TextView(getActivity());
                        sl_head.setText(" SL. ");
                        sl_head.setTextColor(Color.BLACK);
                        sl_head.setGravity(Gravity.CENTER);
                        heading.addView(sl_head);
                        TextView key_head = new TextView(getActivity());
                        key_head.setText(" KEY ");
                        key_head.setTextColor(Color.BLACK);
                        key_head.setGravity(Gravity.CENTER);
                        heading.addView(key_head);
                        TextView val_head = new TextView(getActivity());
                        val_head.setText(" VALUE ");
                        val_head.setTextColor(Color.BLACK);
                        val_head.setGravity(Gravity.CENTER);
                        heading.addView(val_head);
                        sys_table.addView(heading);

                        int i = 1;
                        for (Map.Entry<String,String> entry : system_info.entrySet() ) {
                            TableRow tbrow = new TableRow(getActivity());
                            if (i%2 == 0){
                                tbrow.setBackgroundColor(Color.rgb(211,211,211));
                            }else {
                                tbrow.setBackgroundColor(Color.rgb(192,192,192));
                            }

                            TextView sl_view = new TextView(getActivity());
                            sl_view.setText(String.valueOf(i) + ".");
                            sl_view.setTextSize(12);
                            sl_view.setTextColor(Color.BLACK);
                            sl_view.setGravity(Gravity.CENTER);
                            tbrow.addView(sl_view);
                            TextView key_view = new TextView(getActivity());
                            key_view.setText(entry.getKey());
                            key_view.setTextSize(12);
                            key_view.setTextColor(Color.BLACK);
                            key_view.setGravity(Gravity.CENTER);
                            tbrow.addView(key_view);
                            TextView val_view = new TextView(getActivity());
                            val_view.setText(entry.getValue());
                            val_view.setTextSize(12);
                            val_view.setTextColor(Color.BLACK);
                            val_view.setGravity(Gravity.CENTER);
                            tbrow.addView(val_view);
                            sys_table.addView(tbrow);
                            i= i+1;
                        }
                    }
                });

                rxtx_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        live_table_flag = true;
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                live_table.removeAllViews();
                                WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                WifiInfo wifiinfo = wifiManager.getConnectionInfo();
                                String IP = null;
                                String SSID = null;
                                String BSSID = null;
                                int Rssi = 0;
                                String LinkSpeed = null;
                                String channel = null;
                                int Rx = 0;
                                int Tx = 0;
                                int Rx_Mbps = 0;
                                int Tx_Mbps = 0;
                                if (wifiinfo.getSupplicantState() == SupplicantState.COMPLETED) {
                                    IP = Formatter.formatIpAddress(wifiinfo.getIpAddress());
                                    SSID = wifiinfo.getSSID();
                                    BSSID = wifiinfo.getBSSID();
                                    Rssi = wifiinfo.getRssi();
                                    LinkSpeed = wifiinfo.getLinkSpeed() + " Mbps";
                                    channel = wifiinfo.getFrequency() + "MHz";
                                    Rx = wifiinfo.getRxLinkSpeedMbps();
                                    Tx = wifiinfo.getTxLinkSpeedMbps();
                                    Rx_Mbps = wifiinfo.getRxLinkSpeedMbps();
                                    Tx_Mbps = wifiinfo.getTxLinkSpeedMbps();
                                    System.out.println("Wificlzncz: " + IP + "\n" + SSID + "\n" + BSSID + "\n" + Rssi + "\n" + LinkSpeed + "\n" + channel + "\n" + Rx + "\n" + Tx + "\n" + Rx_Mbps + "\n" + Tx_Mbps);
//                                    System.out.println(wifiinfo);
                                }
                                long availMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                                long totalMem = Runtime.getRuntime().totalMemory();
                                long usedMem = totalMem - availMem;
                                String cpu_used_percent = String.format("%.2f", 100 - (usedMem / (double) totalMem) * 100);
                                Map<String, String> live_data = new HashMap<String, String>();
                                live_data.put("IP", String.valueOf(IP));
                                live_data.put("SSID", SSID);
                                live_data.put("BSSID", BSSID);
                                live_data.put("Rssi", String.valueOf(Rssi));
                                live_data.put("LinkSpeed", LinkSpeed);
                                live_data.put("channel", channel);
                                live_data.put("Rx", String.valueOf(Rx));
                                live_data.put("Tx", String.valueOf(Tx));
                                live_data.put("Rx_Mbps", String.valueOf(Rx_Mbps));
                                live_data.put("Tx_Mbps", String.valueOf(Tx_Mbps));
                                live_data.put("CPU util %", cpu_used_percent);
                                live_table.setPadding(10, 0, 10,0);
                                TableRow heading = new TableRow(getActivity());
                                heading.setBackgroundColor(Color.rgb(120, 156,175));
                                TextView sl_head = new TextView(getActivity());
                                sl_head.setText(" SL. ");
                                sl_head.setTextColor(Color.BLACK);
                                sl_head.setGravity(Gravity.CENTER);
                                heading.addView(sl_head);
                                TextView key_head = new TextView(getActivity());
                                key_head.setText(" KEY ");
                                key_head.setTextColor(Color.BLACK);
                                key_head.setGravity(Gravity.CENTER);
                                heading.addView(key_head);
                                TextView val_head = new TextView(getActivity());
                                val_head.setText(" VALUE ");
                                val_head.setTextColor(Color.BLACK);
                                val_head.setGravity(Gravity.CENTER);
                                heading.addView(val_head);
                                live_table.addView(heading);

                                int i = 1;
                                for (Map.Entry<String,String> entry : live_data.entrySet() ) {
                                    TableRow tbrow = new TableRow(getActivity());
                                    if (i%2 == 0){
                                        tbrow.setBackgroundColor(Color.rgb(211,211,211));
                                    }else {
                                        tbrow.setBackgroundColor(Color.rgb(192,192,192));
                                    }

                                    TextView sl_view = new TextView(getActivity());
                                    sl_view.setText(String.valueOf(i) + ".");
                                    sl_view.setTextSize(12);
                                    sl_view.setTextColor(Color.BLACK);
                                    sl_view.setGravity(Gravity.CENTER);
                                    tbrow.addView(sl_view);
                                    TextView key_view = new TextView(getActivity());
                                    key_view.setText(entry.getKey());
                                    key_view.setTextSize(12);
                                    key_view.setTextColor(Color.BLACK);
                                    key_view.setGravity(Gravity.CENTER);
                                    tbrow.addView(key_view);
                                    TextView val_view = new TextView(getActivity());
                                    val_view.setText(entry.getValue());
                                    val_view.setTextSize(12);
                                    val_view.setTextColor(Color.BLACK);
                                    val_view.setGravity(Gravity.CENTER);
                                    tbrow.addView(val_view);
                                    live_table.addView(tbrow);
                                    i= i+1;
                                }
                                if (live_table_flag){
                                    handler.postDelayed(this, 5000);
                                    System.out.println("Thread ne Aaag lga di...");
                                }else {
                                    handler.removeCallbacks(this);
                                }
                            }
                        };
                        handler.post(r);
                    }
                });


                system_info_btn.performClick();



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

//            public void createtable(String table_name, Vector data ){
//                live_table.setPadding(10, 0, 10,0);
//                TableRow heading = new TableRow(getActivity());
//                heading.setBackgroundColor(Color.rgb(120, 156,175));
//                TextView sl_head = new TextView(getActivity());
//                sl_head.setText(" SL. ");
//                sl_head.setTextColor(Color.BLACK);
//                sl_head.setGravity(Gravity.CENTER);
//                heading.addView(sl_head);
//                TextView key_head = new TextView(getActivity());
//                key_head.setText(" KEY ");
//                key_head.setTextColor(Color.BLACK);
//                key_head.setGravity(Gravity.CENTER);
//                heading.addView(key_head);
//                TextView val_head = new TextView(getActivity());
//                val_head.setText(" VALUE ");
//                val_head.setTextColor(Color.BLACK);
//                val_head.setGravity(Gravity.CENTER);
//                heading.addView(val_head);
//                live_table.addView(heading);
//
//                int i = 1;
//                for (Map.Entry<String,String> entry : data.entrySet() ) {
//                    TableRow tbrow = new TableRow(getActivity());
//                    if (i%2 == 0){
//                        tbrow.setBackgroundColor(Color.rgb(211,211,211));
//                    }else {
//                        tbrow.setBackgroundColor(Color.rgb(192,192,192));
//                    }
//
//                    TextView sl_view = new TextView(getActivity());
//                    sl_view.setText(String.valueOf(i) + ".");
//                    sl_view.setTextSize(12);
//                    sl_view.setTextColor(Color.BLACK);
//                    sl_view.setGravity(Gravity.CENTER);
//                    tbrow.addView(sl_view);
//                    TextView key_view = new TextView(getActivity());
//                    key_view.setText(entry.getKey());
//                    key_view.setTextSize(12);
//                    key_view.setTextColor(Color.BLACK);
//                    key_view.setGravity(Gravity.CENTER);
//                    tbrow.addView(key_view);
//                    TextView val_view = new TextView(getActivity());
//                    val_view.setText(entry.getValue());
//                    val_view.setTextSize(12);
//                    val_view.setTextColor(Color.BLACK);
//                    val_view.setGravity(Gravity.CENTER);
//                    tbrow.addView(val_view);
//                    live_table.addView(tbrow);
//                    i= i+1;
//                }
//
//            }
        });
        return root;



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
