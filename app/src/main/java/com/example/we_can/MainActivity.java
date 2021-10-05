package com.example.we_can;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorSpace;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
//        Button b = findViewById(R.id.btn);


    }
}