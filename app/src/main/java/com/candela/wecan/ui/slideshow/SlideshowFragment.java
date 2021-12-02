package com.candela.wecan.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.candela.wecan.R;
import com.candela.wecan.databinding.FragmentSlideshowBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Button refresh;
                refresh = getActivity().findViewById(R.id.refresh_btn);
                try {
//                    Process clear_process = Runtime.getRuntime().exec("logcat -c");
                    Process process = Runtime.getRuntime().exec("logcat -d");
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    StringBuilder log = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        log.append(line);
                    }
                    TextView tv = (TextView) getActivity().findViewById(R.id.logcat_tv);
                    tv.setText(log.toString());
                }
                catch (IOException e) {}

                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
//                            Process clear_process = Runtime.getRuntime().exec("logcat -c");
                            Process process = Runtime.getRuntime().exec("logcat -d");
                            BufferedReader bufferedReader = new BufferedReader(
                                    new InputStreamReader(process.getInputStream()));

                            StringBuilder log = new StringBuilder();
                            String line = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                log.append(line);
                            }
                            TextView tv = (TextView) getActivity().findViewById(R.id.logcat_tv);
                            tv.setText(log.toString());
                        }
                        catch (IOException e) {}
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

