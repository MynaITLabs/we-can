package com.candela.wecan.tests.base_tools;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class file_handler extends AppCompatActivity {
    private static final String FILE_NAME = "data.conf";
    private String text_data;
    public static Context context;

    public file_handler(Context context){

            this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void set_val(String key, String value){
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            Map<String, String> dataMap = new HashMap<String, String>();
            fis = this.context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            text_data= br.readLine();
            String updated_string =null;// "SALES:0,SALE_PRODUCTS:1,EXPENSES:2,EXPENSES_ITEMS:3";

            String[] pairs = text_data.split(",");
            for (int i=0;i<pairs.length;i++) {
                String pair = pairs[i];
                String[] keyValue = pair.split(":");
                dataMap.put(keyValue[0], keyValue[1]);
            }
            dataMap.put(key, value);
            for (String i : dataMap.keySet()) {
                String keyVal = i + ":" + dataMap.get(i);
                updated_string.concat(keyVal + ',');
            }

            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(updated_string.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public String get_val(String key){
        FileInputStream fis = null;
        try {
            Map<String, String> dataMap = new HashMap<String, String>();
            fis = this.context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            text_data = br.readLine();
            String[] pairs = text_data.split(",");
            for (int i=0;i<pairs.length;i++) {
                String pair = pairs[i];
                String[] keyValue = pair.split(":");
                dataMap.put(keyValue[0], keyValue[1]);
            }
            String value = dataMap.get(key);
            return value;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return key;
    }

}