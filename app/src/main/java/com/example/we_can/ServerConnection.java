package com.example.we_can;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.we_can.tests.base_tools.HTTPHandler;

public class ServerConnection extends AppCompatActivity {

    public static HTTPHandler http_server_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_connection);
        getSupportActionBar().hide();
        EditText server_entry = findViewById(R.id.server_entry);

        Button button = findViewById(R.id.server_connect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = server_entry.getText().toString();
                http_server_handler = new HTTPHandler(ip);
            }
        });
    }
}