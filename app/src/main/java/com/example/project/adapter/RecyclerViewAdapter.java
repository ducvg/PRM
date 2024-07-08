package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TodayViewHolder> {
    private List<Task> list;

    public RecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Task> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public Task getTask(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public TodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder{
        private TextView title,desc,date,category;
        private Button edit;
        private CheckBox check;

        public TodayViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.todoTitle);
            desc = itemView.findViewById(R.id.todoDescription);
            date = itemView.findViewById(R.id.todoDateTime);
            category = itemView.findViewById(R.id.todoCategory);
            edit = itemView.findViewById(R.id.todoBtnEdit);
            check = itemView.findViewById(R.id.todoCheckBox);


        }
    }
}