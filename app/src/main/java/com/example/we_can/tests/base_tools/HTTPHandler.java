package com.example.we_can.tests.base_tools;

import java.net.MalformedURLException;
import java.net.URL;

public class HTTPHandler {
    String url;
    int copnn_status_code;
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
    public static int setup_connection(String url){
        // Write A json code to initialize the connection
        return 0;
    }


}
