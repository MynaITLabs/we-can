package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.candela.wecan.tests.base_tools.CardUtils;
import com.candela.wecan.tests.base_tools.LF_Resource;
import com.candela.wecan.tests.base_tools.file_handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Permission;
import java.util.Map;

//import candela.lfresource.lfresource;
//import com.candela.wecan.tests.base_tools.LF_Resource;


/**
 * Startup Activity for Candela WE-CAN
 *
 */
public class StartupActivity extends AppCompatActivity {

    private Button button;
    static final int STARTING = 0;
    static final int RUNNING = 1;
    static final int STOPPED = 2;
    private static final String FILE_NAME = "data.conf";
    private TextView server_ip;
    static int state;
    private String ssid, passwd;
    public Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getSupportActionBar().hide();
        button = (Button) findViewById(R.id.enter_button);
        server_ip = findViewById(R.id.ip_enter_page);
        SharedPreferences sharedpreferences = getBaseContext().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        Map<String,?> keys = sharedpreferences.getAll();
        String last_ip = (String) keys.get("last");
        server_ip.setText(last_ip);
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 1);
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 2);
        checkPermission(Manifest.permission.ACCESS_WIFI_STATE, 3);
        checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, 4);
        checkPermission(Manifest.permission.CHANGE_WIFI_STATE, 5);
//        Intent myIntent = new Intent(this, ClientConnectivityConfiguration.class);
//        startActivity(myIntent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (keys.keySet().contains(server_ip.getText().toString())){
                    Map<String,?> keys = sharedpreferences.getAll();
                    String ip = server_ip.getText().toString();
                    String realm_id = (String) keys.get("realm-" + ip);
                    String resource_id = (String) keys.get("resource-" + ip);
                    connect_server(server_ip.getText().toString(), resource_id, realm_id, view, sharedpreferences);
                }
                else{
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(server_ip.getText().toString(), "-1");
                    editor.putString("resource-" + server_ip.getText().toString(), "-1");
                    editor.putString("realm-" + server_ip.getText().toString(), "-1");
                    editor.apply();
                    editor.commit();
                    Map<String,?> keys = sharedpreferences.getAll();
                    String ip = server_ip.getText().toString();
                    String realm_id = (String) keys.get("realm-" + ip);
                    String resource_id = (String) keys.get("resource-" + ip);
                    connect_server(server_ip.getText().toString(), resource_id, realm_id, view, sharedpreferences);
                }


            }
        });
    }

        public void openServerConnection () {

            Intent myIntent = new Intent(this, navigation.class);
            startActivity(myIntent);

        }

        public void connect_server(String ip, String resource_id, String realm_id, View v, SharedPreferences sharedPreferences){
            LF_Resource p = new LF_Resource(143, ip, resource_id, realm_id, getApplicationContext());
            p.start();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    state = p.lfresource.get_state();
                    if (p.getConnState()){
                        Toast.makeText(v.getContext(), "Connected to LANforge Server", Toast.LENGTH_LONG).show();
                        String new_resource_id = p.getResource();
                        String new_realm_id = p.getRealm();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(ip, "-1");
                        editor.putString("last", ip);
                        editor.putString("resource-" + ip, new_resource_id);
                        editor.putString("realm-" + ip, new_realm_id);

                        editor.putString("current-ip", ip);
                        editor.putString("current-resource", new_resource_id);
                        editor.putString("current-realm", new_realm_id);

                        editor.apply();
                        editor.commit();
//                        CardUtils cardUtils = new CardUtils(getApplicationContext());
                        openServerConnection();
                    }
                }
            }, 1000);
        }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(StartupActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(StartupActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(StartupActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }


}