package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.candela.wecan.tests.base_tools.LF_Resource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.candela.wecan.tests.base_tools.file_handler;
import com.candela.wecan.tests.base_tools.file_handler.*;
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
    //    private Boolean server_connected_status = false;
    private String ip, ssid, passwd;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getSupportActionBar().hide();
        button = (Button) findViewById(R.id.enter_button);
        server_ip = findViewById(R.id.ip_enter_page);
//        LF_Resource p = new LF_Resource(143, "192.168.52.100", "2");
//        p.start();

        file_handler file_obj = new file_handler(getApplicationContext());
//        file_obj.set_val("Server", "192.168.200.15");
        ip = file_obj.get_val("SALE_PRODUCTS");
        server_ip.setText(ip);
        Log.d("SALE_PRODUCTS: ", ""+ ip);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                file_handler file_obj = new file_handler(getApplicationContext());
                file_obj.set_val("Key", "Value");
                String txt = file_obj.get_val("SALES");

                System.out.println("Data SALES "+txt);
                String ip = server_ip.getText().toString().trim();
                String data = ip + "\n" + ssid + "\n" + passwd;
                if( ip.length() == 0 )
                    server_ip.setError( "IP is required!" );
                else{
//                    Toast.makeText(v.getContext(), "Configuration Saved Successfully ", Toast.LENGTH_SHORT).show();
                file_handler file_obj1 = new file_handler(getApplicationContext());
//        file_obj.set_val("Server", "192.168.200.15");
                ip = file_obj1.get_val("SALE_PRODUCTS");
                server_ip.setText(ip);

                }


                LF_Resource p = new LF_Resource(143, ip, "2");
                p.start();
                state = p.lfresource.get_state();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        state = p.lfresource.get_state();
                        if (state == STOPPED){
                            Toast.makeText(v.getContext(), "Server STOPPED", Toast.LENGTH_LONG).show();
                        }
                        if (state == STARTING){
                            Toast.makeText(v.getContext(), "Server is STARTING", Toast.LENGTH_LONG).show();
                        }
                        if (state == RUNNING){
                            Toast.makeText(v.getContext(), "Server is RUNNING", Toast.LENGTH_LONG).show();
                        }
                    }
                }, 1000);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.removeCallbacks(this);
                        state = p.lfresource.get_state();
                        if (state == RUNNING){
                            Toast.makeText(v.getContext(), "Connected to LANforge Server", Toast.LENGTH_LONG).show();
                            openServerConnection();
                        }
                    }
                }, 1000);


            }
        });
//        LF_Resource p = new LF_Resource(143);
//        p.start();
    }

//
//        Intent myIntent = new Intent(this, ServerConnection.class);
//        startActivity(myIntent);

    public void openServerConnection () {

        Intent myIntent = new Intent(this, navigation.class);
        startActivity(myIntent);


    }
}