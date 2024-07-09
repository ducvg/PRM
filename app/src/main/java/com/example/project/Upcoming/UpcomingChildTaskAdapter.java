package com.example.project.Upcoming;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.model.Task;

import java.util.List;

public class UpcomingChildTaskAdapter extends RecyclerView.Adapter<UpcomingChildTaskViewHolder> {
    private List<Task> dayTask;

    public UpcomingChildTaskAdapter(List<Task> dayTask) {
        this.dayTask = dayTask;
    }

    @NonNull
    @Override
    public UpcomingChildTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingChildTaskViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
