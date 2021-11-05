package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import candela.lfresource.lfresource;
import com.candela.wecan.databinding.ActivityStartupBinding;
import com.candela.wecan.tests.IPERF;
import com.candela.wecan.tests.base_tools.LF_Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Startup Activity for Candela WE-CAN
 *
 */
public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getSupportActionBar().hide();
        LF_Resource p = new LF_Resource(143);
        p.start();
        //        Thread thread = new Thread();
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 1000);
//        lfresource lfresource = new lfresource();
//        String[] args = new String[6];
//        args[0] = "-s";
//        args[1] = "192.168.52.100"; //.put("-s", "192.168.100.222");
//        args[2] = "--resource"; //.put("-s", "192.168.100.222");
//        args[3] =  "3";
////                  //.put("-s", "192.168.100.222");
//        args[4] = "--realm";
//        args[5] = "222";
//
////        { "-s", "192.168.100.222", "--resource", "2", "--realm", "222" }
////        lfresource.init(false, args);


        /**
         * Switch Activity from here
         */

//        Intent myIntent = new Intent(this, ServerConnection.class);
//        startActivity(myIntent);

    }
}

