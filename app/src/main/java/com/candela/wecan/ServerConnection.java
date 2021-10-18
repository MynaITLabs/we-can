package com.candela.wecan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.candela.wecan.tests.base_tools.HTTPHandler;

public class ServerConnection extends AppCompatActivity {

    public static HTTPHandler http_server_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_connection);
        getSupportActionBar().hide();
        EditText server_entry = findViewById(R.id.server_entry);
        ProgressBar progressBar = findViewById(R.id.server_state_pb);
        progressBar.setVisibility(View.INVISIBLE);
        Button button = findViewById(R.id.server_connect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip =  "http://" + server_entry.getText().toString().replace(" ","") + ":8038";
                http_server_handler = new HTTPHandler(ip, getApplicationContext());
                int state = http_server_handler.server_state_get();
                final Handler handler = new Handler(Looper.getMainLooper());
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        if (http_server_handler.get_server_state() == 1){
                            handler.removeCallbacks(this::run);
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(ServerConnection.this, TestActivity.class);
//                            intent.putExtra("server_handler", (Parcelable) http_server_handler);
                            startActivity(intent);
                        }
                    }
                }, 100);


                int temp = 1;
                if (temp == 1){
                    // Start a activity
//
                }

//                if (http_server_handler.copnn_status_code == 1){
//                    // Start a activity
//                    Intent intent = new Intent(ServerConnection.this, TestActivity.class);
//                    intent.putExtra("server_handler", http_server_handler);
//                    startActivity(intent);
//                }
//                else {
//                    // Give error
//                }
            }
        });
    }
}