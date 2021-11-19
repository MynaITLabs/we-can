package com.candela.wecan.tests.base_tools;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class file_handler extends AppCompatActivity {
    private static final String FILE_NAME = "data.conf";
    private String text_data;

    public file_handler(){
    }

    public void save_db(String ip, String new_resource_id, String new_realm_id){
        System.out.println("chaman");
        SharedPreferences sprf = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        try {
            SharedPreferences.Editor editor = sprf.edit();
            editor.putString("ip", ip);
            editor.putString("realm", new_realm_id);
            editor.putString("resource", new_resource_id);
            editor.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("onClick: ", "IN File Handler ");
        Log.d("onClick: ", "Data ==> " + ip);
        Log.d("onClick: ", "RES_ID ==> " + new_resource_id);
        Log.d("onClick: ", "REALM_ID ==> " + new_realm_id);
    }



    public String getip(){
        String ip;
            SharedPreferences sprf = getSharedPreferences("userdata", Context.MODE_PRIVATE);
            ip = sprf.getString("ip", "");
            return ip;
    }

}