package com.example.project.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter {

    public class TodayViewHolder extends RecyclerView.ViewHolder{
        private TextView title,desc,date;
        private Button edit;
        public TodayViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class UpcomingViewHolder extends RecyclerView.ViewHolder{

        public UpcomingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
