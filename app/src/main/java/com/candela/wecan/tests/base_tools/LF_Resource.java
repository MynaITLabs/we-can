package com.candela.wecan.tests.base_tools;

import java.util.Vector;

import candela.lfresource.AndroidUI;
import candela.lfresource.LANforgeMgr;
import candela.lfresource.PlatformInfo;
import candela.lfresource.StringKeyVal;
import candela.lfresource.lfresource;
//LF_Resource p = new LF_Resource(143);
//        p.start();
public class LF_Resource extends Thread {

    long minPrime;

    public lfresource lfresource;
    public PlatformInfo pi;
    public String ip_address;
    public String resource;

    public LF_Resource(long minPrime, String ip_address, String resource) {
        this.minPrime = minPrime;
        this.lfresource = new lfresource();
        this.ip_address = ip_address;
        this.resource = resource;
        this.pi = new PlatformInfo();

        this.pi.manufacturer = "samsung";
        this.pi.model = "a11";
        this.pi.wifi_capabilities = new Vector<>();
        this.pi.dhcp_info = new Vector<>();
        this.pi.username = "";

    }

    public String getResource(){
        return resource;
    }

    public void run() {
        // compute primes larger than minPrime

        String[] args = new String[6];
        args[0] = "-s";
        args[1] = this.ip_address; //.put("-s", "192.168.100.222");
        args[2] = "--resource"; //.put("-s", "192.168.100.222");
        args[3] = this.resource;
//        args[4] = "--realm"; //.put("-s", "192.168.100.222");
//        args[5] =  "-1";
        this.lfresource.init(false, args);
        LANforgeMgr.setPlatformInfo(this.pi);

        AndroidUI androidUI = new AndroidUI() {
            @Override
            public void setResourceInfo(int i, int i1) {

            }

            @Override
            public Vector<StringKeyVal> requestPortUpdate(String s) {
                return null;
            }
        };
        short i =LANforgeMgr.getResourceId();
        resource = String.valueOf(i);
//        System.out.println("Shivamiron:" + resource);
    }
}
