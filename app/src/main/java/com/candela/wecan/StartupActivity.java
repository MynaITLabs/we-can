package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//import candela.lfresource.lfresource;
import com.candela.wecan.databinding.ActivityStartupBinding;
import com.candela.wecan.tests.IPERF;
//import com.candela.wecan.tests.base_tools.LF_Resource;

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
//        LF_Resource p = new LF_Resource(143);
//        p.start();

//
//        Intent myIntent = new Intent(this, ServerConnection.class);
//        startActivity(myIntent);

        Intent myIntent = new Intent(this, navigation.class);
        startActivity(myIntent);


    }
}
