package com.example.project.fragment.Settings;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class SettingAdapter extends RecyclerView.Adapter<SettingViewHolder>{

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
