package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.candela.wecan.databinding.ActivityStartupBinding;

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

        Intent myIntent = new Intent(this, ServerConnection.class);
        startActivity(myIntent);

    }
}

