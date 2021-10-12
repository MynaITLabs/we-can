package com.example.we_can.tests.base_tools;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

public class HTTPHandler implements Parcelable {
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

    protected HTTPHandler(Parcel in) {
        url = in.readString();
    }

    public static final Creator<HTTPHandler> CREATOR = new Creator<HTTPHandler>() {
        @Override
        public HTTPHandler createFromParcel(Parcel in) {
            return new HTTPHandler(in);
        }

        @Override
        public HTTPHandler[] newArray(int size) {
            return new HTTPHandler[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
    }
}
