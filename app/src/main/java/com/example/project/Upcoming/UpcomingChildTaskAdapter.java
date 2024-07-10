package com.example.project.Upcoming;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;

import java.time.LocalDate;
import java.util.List;

public class UpcomingChildTaskAdapter extends RecyclerView.Adapter<UpcomingChildTaskViewHolder> implements View.OnClickListener {
    private List<Task> dayTask;
    private UpcomingTaskAdapter.OnItemClickListener listener;

    public UpcomingChildTaskAdapter(List<Task> dayTask, UpcomingTaskAdapter.OnItemClickListener listener) {
        this.dayTask = dayTask;
        this.listener = listener;
        Log.d("debug children cons",String.valueOf(dayTask.size()));

    }

    @NonNull
    @Override
    public UpcomingChildTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_upcoming, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = parent.getHeight();
        return new UpcomingChildTaskViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingChildTaskViewHolder holder, int position) {

        Task task = dayTask.get(position);
        holder.setData(task);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    @Override
    public void onClick(View v) {

    }
}
