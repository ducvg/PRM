package com.example.project.Upcoming;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.CalendarUtils;
import com.example.project.R;
import com.example.project.fragment.FragmentUpcoming;
import com.example.project.model.Task;

import java.time.LocalDate;
import java.util.List;

public class UpcomingTaskAdapter extends RecyclerView.Adapter<UpcomingTaskViewHolder> {
    private List<LocalDate> yearDays;
    private List<Task> dayTask;
    private OnItemClickListener listener;

    public UpcomingTaskAdapter(List<LocalDate> yearDays, OnItemClickListener listener) {
        this.yearDays = yearDays;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UpcomingTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_upcoming_task, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = parent.getHeight();
        return new UpcomingTaskViewHolder(view, yearDays, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingTaskViewHolder holder, int position) {
        LocalDate date = yearDays.get(position);
        holder.setData(date);

    }

    @Override
    public int getItemCount() {
        return yearDays.size();
    }

    public interface OnItemClickListener {
        void onItemClick(LocalDate day, int position);
    }
}

