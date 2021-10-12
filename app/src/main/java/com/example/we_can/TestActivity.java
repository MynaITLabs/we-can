package com.example.we_can;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.we_can.tests.BasicClientConnectivity;
import com.example.we_can.tests.SpeedTest;
import com.example.we_can.tests.base_tools.HTTPHandler;

import android.os.Build;
import android.widget.RadioGroup;

import java.sql.Timestamp;


public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().hide();
        Button b = findViewById(R.id.button);
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        HTTPHandler httpHandler = getIntent().getParcelableExtra("server_handler");
        IntentFilter intentFilter = new IntentFilter();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getApplicationContext(), ClientConnectivityConfiguration.class);
                myIntent.putExtra("server_handler", httpHandler);
                startActivity(myIntent);

            }
        });
        Button b1 = findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeedTest obj = new SpeedTest();
            }
        });
    }




}