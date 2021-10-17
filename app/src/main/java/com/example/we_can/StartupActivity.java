package com.example.we_can;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;

import com.example.we_can.databinding.ActivityStartupBinding;
import com.example.we_can.tests.base_tools.HTTPHandler;
import com.example.we_can.ui.login.LoginActivity;

import java.io.IOException;

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

