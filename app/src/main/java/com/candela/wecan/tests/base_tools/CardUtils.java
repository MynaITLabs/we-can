package com.candela.wecan.tests.base_tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import candela.lfresource.Card;
import candela.lfresource.LANforgeMgr;
import candela.lfresource.Port;
import candela.lfresource.WifiInterfaceConfig;

public class CardUtils {
    public Card card;
    public Port wifi_port;

    public CardUtils(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        Map<String,?> keys = sharedpreferences.getAll();
        String current_resource = (String) keys.get("current-resource");
        String current_realm = (String) keys.get("current-realm");
        this.card = LANforgeMgr.findCard();
        //        Card card = new Card(Integer.valueOf(current_realm), Integer.valueOf(current_resource));
        Port[] ports = card.ports;
        this.card.refreshStats();
        WifiInterfaceConfig wifiInterfaceConfig = new WifiInterfaceConfig();

//        Port port = card.findPort("wlan0");
//        port.setName("shivam");
        for (int i=0; i<=ports.length; i++){
            try{
                if (ports[i].getDevName().equals("wlan0")){
                    this.wifi_port = card.findPort("wlan0");
//                    wifiInterfaceConfig.setAP(this.wifi_port, "shivam", true);
                    //                    this.wifi_por
//                    this.wifi_port.setName("shivam");
                    this.wifi_port.sendReport(1);
                    card.sendUpdate(1);

                    System.out.println("gopi "+wifiInterfaceConfig.getAP());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
