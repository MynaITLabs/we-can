package com.candela.wecan.tests.base_tools;

import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class Logcat extends AppCompatActivity {
    Logcat logca = new Logcat();
    public void sg(){
        if ( logca.isExternalStorageWritable() ) {

            File appDirectory = new File( Environment.getExternalStorageDirectory() + "/WE-CAN" );
            File logDirectory = new File( appDirectory + "/logs" );
            File logFile = new File( logDirectory, "logcat_" + System.currentTimeMillis() + ".txt" );

            // create app folder
            if ( !appDirectory.exists() ) {
                appDirectory.mkdir();
                Toast.makeText(getApplicationContext(),"folder created",
                        Toast.LENGTH_LONG).show();
                if( appDirectory.exists()) {
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }
            }

            // create log folder
            if ( !logDirectory.exists() ) {
                logDirectory.mkdir();
                Toast.makeText(getApplicationContext(),"log folder created",
                        Toast.LENGTH_LONG).show();
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            }

            // clear the previous logcat and then write the new one to the file
            try {
                //@SuppressWarnings("unused")
                Process process = Runtime.getRuntime().exec("logcat -c");
                process = Runtime.getRuntime().exec("logcat -f " + logFile);

            } catch ( IOException e ) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Exec command not executed",
                        Toast.LENGTH_LONG).show();
                System.out.println("Command not executed");
            }

        } else if ( logca.isExternalStorageReadable() ) {
            Toast.makeText(getApplicationContext(),"Only read",
                    Toast.LENGTH_LONG).show();
            System.out.println("External Storage notttttttttttttttttttttttttttt");
            // only readable
        } else {
            // not accessible
            Toast.makeText(getApplicationContext(),"Not Accessible",
                    Toast.LENGTH_LONG).show();
            System.out.println("Not accessibleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        }

        /* Checks if external storage is available to at least read */
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            Toast.makeText(getApplicationContext(),"External Storage readable",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            Toast.makeText(getApplicationContext(),"External storaeg writable",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }


}
