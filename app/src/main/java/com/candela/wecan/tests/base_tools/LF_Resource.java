package com.candela.wecan.tests.base_tools;

import android.content.Context;

import java.util.Vector;

import candela.lfresource.AndroidUI;
import candela.lfresource.LANforgeMgr;
import candela.lfresource.PlatformInfo;
import candela.lfresource.StringKeyVal;
import candela.lfresource.lfresource;
//LF_Resource p = new LF_Resource(143);
//        p.start();
public class LF_Resource extends Thread {

    private String realm_id;
    long minPrime;

    public lfresource lfresource;
    public PlatformInfo pi;
    public String ip_address;
    public String resource;
    public Context context;


    public LF_Resource(long minPrime, String ip_address, String resource, String realm_id, Context context) {
        this.minPrime = minPrime;
        this.context = context;
        this.lfresource = new lfresource();
        this.ip_address = ip_address;
        this.resource = resource;
        this.realm_id = realm_id;
        this.pi = new PlatformInfo();

        this.pi.manufacturer = "samsung";
        this.pi.model = "a11";
        this.pi.wifi_capabilities = new Vector<>();
        this.pi.dhcp_info = new Vector<>();
        this.pi.username = "";
        ResourceUtils ru = new ResourceUtils(this.context);
        ru.requestPortUpdate("");

    }

    public String getResource(){
        return String.valueOf(LANforgeMgr.getResourceId());

    }

    public String getRealm(){
        return String.valueOf(LANforgeMgr.getRealmId());

    }

    public void run() {
        // compute primes larger than minPrime

        String[] args = new String[6];
        args[0] = "-s";
        args[1] = this.ip_address; //.put("-s", "192.168.100.222");
        args[2] = "--resource"; //.put("-s", "192.168.100.222");
        args[3] = this.resource;
        args[4] = "--realm"; //.put("-s", "192.168.100.222");
        args[5] = this.realm_id;
        this.lfresource.init(false, args);
//        LANforgeMgr.setPlatformInfo(this.pi);


    }
}
