package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.candela.wecan.tests.SpeedTest;
import com.candela.wecan.tests.base_tools.GetPhoneWifiInfo;


public class TestActivity extends AppCompatActivity {

    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
        getSupportActionBar().hide();
        Button b = findViewById(R.id.button);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        GetPhoneWifiInfo getPhoneWifiInfo = new GetPhoneWifiInfo();
        getPhoneWifiInfo.GetWifiData(wifiManager);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getApplicationContext(), ClientConnectivityConfiguration.class);
//                myIntent.putExtra("server_handler", httpHandler);
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