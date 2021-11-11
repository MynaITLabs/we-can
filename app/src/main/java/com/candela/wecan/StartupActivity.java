package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//import candela.lfresource.lfresource;
//import com.candela.wecan.tests.base_tools.LF_Resource;


/**
 * Startup Activity for Candela WE-CAN
 *
 */
public class StartupActivity extends AppCompatActivity {
    private Button button;
    private static final String FILE_NAME = "data.conf";
    private Boolean server_connected_status = true;
    private TextView server_ip;
    private String ip, ssid, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getSupportActionBar().hide();
        button = (Button) findViewById(R.id.enter_button);
        server_ip = findViewById(R.id.ip_enter_page);

        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            ip= br.readLine();
            ssid = br.readLine();
            passwd = br.readLine();

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
                String data = ip + "\n" + ssid + "\n" + passwd;
                if( ip.length() == 0 )
                    server_ip.setError( "IP is required!" );
                else{

                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                        fos.write(data.getBytes(StandardCharsets.UTF_8));
//                        Toast.makeText(v.getContext(), "Configuration Saved Successfully ", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (fos != null){
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Log.d( "onClick: ", "Data ==> " + ip);
                }
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