package com.candela.wecan.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.candela.wecan.R;
import com.candela.wecan.databinding.FragmentHomeBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button refresh_button;
    private static final String FILE_NAME = "data.conf";
    private TextView ip_show;
    public String ip, ip1;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ip_show = getView().findViewById(R.id.server_ip_info);
                SharedPreferences sprf = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                ip = sprf.getString("ip", "192.168.0.0") + "\n" + sprf.getString("realm_id", "") + "\n" + sprf.getString("resource_id", "");
                    Log.d("onChangedIP: ", ip);
                    ip_show.setText(ip);
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