package com.candela.wecan.ui.gallery;

import android.content.Context;
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


//        //IP READER
//        FileInputStream fis = null;
//        try {
//            fis = getActivity().openFileInput(FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String text;
//
//            while ((text= br.readLine()) != null){
//                sb.append(text).append("\n");
//
//            }
//            server_ip.setText(sb.toString());
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null){
//                try {
//                    fis.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }

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
                        String data = ip + "\n" + ssid + "\n" + password;
                        if( ip.length() == 0 )
                            server_ip.setError( "IP is required!" );
                        else{

                        FileOutputStream fos = null;
                        try {
                            fos = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                            fos.write(data.getBytes(StandardCharsets.UTF_8));
                            server_ip.getText().clear();
                            ssid_name.getText().clear();
                            pass.getText().clear();
                            Toast.makeText(v.getContext(), "Configuration Saved Successfully ", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (fos != null){
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        Log.d( "onClick: ", "Data ==> " + ip);
                    }
                    }
                });

                load_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FileInputStream fis = null;
                        try {
                            fis = getActivity().openFileInput(FILE_NAME);
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr);
                            StringBuilder sb = new StringBuilder();
                            String ip, ssid,passwd;

                            ip= br.readLine();
                            ssid = br.readLine();
                            passwd = br.readLine();


//                            while ((text= br.readLine()) != null){
//                                sb.append(text).append("\n");
//
//                            }
//                            server_ip.setText(sb.toString());
                                server_ip.setText(ip);
                                ssid_name.setText(ssid);
                                pass.setText(passwd);
                            Log.d("onClick: ", "IP: " + ip);
                            Log.d("onClick", "ssid: " + ssid);
                            Log.d("onClick", "Password: " + passwd);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fis != null){
                                try {
                                    fis.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
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