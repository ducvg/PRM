package com.example.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.fragment.Settings.SettingAdapter;
import com.example.project.fragment.Today.TodayTaskAdapter;
import com.example.project.main.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class FragmentSettings extends Fragment {
    private SettingAdapter adapter;
    private RecyclerView rcvSetting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        adapter = new SettingAdapter();
        rcvSetting.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvSetting.setAdapter(adapter);
    }

    private void bindingView() {
        rcvSetting = getView().findViewById(R.id.rcvSetting);
    }


}
