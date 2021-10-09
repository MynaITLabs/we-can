package com.example.we_can.tests.base_tools;

import java.net.MalformedURLException;
import java.net.URL;

public class HTTPHandler {
    String url;
    public static int copnn_status_code;
    public HTTPHandler(String url){
        this.url = url;

        try {

            URL httpbinEndpoint = new URL(url);
            this.copnn_status_code = this.setup_connection(url);
        } catch (MalformedURLException e) {
            this.copnn_status_code = 0;
            e.printStackTrace();
        }

    }
    public static int setup_connection(String url) {
        // Write code to initialize the connection
        // {"request": "connection_check"}
        // {"response": "Alive"}
        // http://192.168.200.15:8805/get/request
        int response_code = 1;
        if (response_code == 200) {
            return 1;
        } else {
            return 0;
        }
    }


}
