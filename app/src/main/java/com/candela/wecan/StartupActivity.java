package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

//import candela.lfresource.lfresource;
//import com.candela.wecan.tests.base_tools.LF_Resource;


/**
 * Startup Activity for Candela WE-CAN
 *
 */
public class StartupActivity extends AppCompatActivity {
    private Button button;
    private static final String FILE_NAME = "data.conf";
    private Boolean server_connected_status = false;
    private TextView server_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getSupportActionBar().hide();
        button = (Button) findViewById(R.id.enter_button);
        server_ip = findViewById(R.id.ip_enter_page);

        FileInputStream fis = null;
        try {
            fis =openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String ip;

            ip= br.readLine();
            server_ip.setText(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = server_ip.getText().toString();

                if (server_connected_status){
                    openServerConnection();
                }
                else {
                    Toast.makeText(v.getContext(), "Server Down", Toast.LENGTH_LONG).show();
                }


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