package com.hongbojiang.ui.inspect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hongbojiang.R;

public class InspectFragment extends Fragment {

    private InspectViewModel inspectViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inspectViewModel =
                ViewModelProviders.of(this).get(InspectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inspect, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        inspectViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}