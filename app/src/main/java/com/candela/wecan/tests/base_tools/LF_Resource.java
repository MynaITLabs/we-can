package com.candela.wecan.tests.base_tools;

import candela.lfresource.lfresource;

public class LF_Resource extends Thread {
    long minPrime;
    public LF_Resource(long minPrime) {
        this.minPrime = minPrime;
    }

    public void run() {
        // compute primes larger than minPrime
        lfresource lfresource = new lfresource();
        String[] args = new String[6];
        args[0] = "-s";
        args[1] = "192.168.52.100"; //.put("-s", "192.168.100.222");
        args[2] = "--resource"; //.put("-s", "192.168.100.222");
        args[3] =  "2";
//                  //.put("-s", "192.168.100.222");
        args[4] = "--realm";
        args[5] = "222";
        lfresource.init(false, args);

    }
}
