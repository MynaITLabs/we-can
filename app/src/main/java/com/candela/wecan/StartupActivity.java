package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.candela.wecan.databinding.ActivityStartupBinding;
import com.candela.wecan.tests.IPERF;

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

        /**
         * Switch Activity from here
         */
//        TestActivity test = new TestActivity();
//        IPERF obj = new IPERF(test, "", "", "", "");


        Intent myIntent = new Intent(this, navigation.class);
        startActivity(myIntent);

    }
}

