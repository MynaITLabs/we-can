package com.candela.wecan.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.candela.wecan.R;
import com.candela.wecan.databinding.FragmentGalleryBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private static final String FILE_NAME = "data.conf";
    private Button save_button,load_button;
    private EditText server_ip, ssid_name, pass;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                save_button = getView().findViewById(R.id.save);
                load_button = getView().findViewById(R.id.load_button);

                server_ip = getView().findViewById(R.id.server_ip);
                ssid_name = getView().findViewById(R.id.ssid);
                pass = getView().findViewById(R.id.ssid_password);

                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ip = server_ip.getText().toString();
                        String ssid = ssid_name.getText().toString();
                        String password = pass.getText().toString();
                        if( ip.length() == 0 )
                            server_ip.setError( "IP is required!" );
                        else{
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("current-ip", ip);
                            editor.putString("current-ssid", ssid);
                            editor.putString("current-passwd", password);
                            editor.apply();
                            editor.commit();
                            server_ip.setText("");
                            ssid_name.setText("");
                            pass.setText("");

                    }
                    }
                });

                load_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String ip, ssid,passwd;
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                            Map<String,?> keys = sharedPreferences.getAll();
//                            String current_ip = (String) keys.get("current-ip");
                            ip= (String) keys.get("current-ip");
                            ssid = (String) keys.get("current-ssid");
                            passwd = (String) keys.get("current-passwd");
                                server_ip.setText(ip);
                                ssid_name.setText(ssid);
                                pass.setText(passwd);
                            Log.d("onClick: ", "IP: " + ip);
                            Log.d("onClick", "ssid: " + ssid);
                            Log.d("onClick", "Password: " + passwd);
                        } finally {
                        }
                    }
                });
            }
        });
        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}