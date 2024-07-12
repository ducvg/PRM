package com.example.project.Upcoming;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.CalendarUtils;
import com.example.project.R;
import com.example.project.dal.SQLiteHelper;
import com.example.project.fragment.FragmentUpcoming;
import com.example.project.model.Task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpcomingTaskAdapter extends RecyclerView.Adapter<UpcomingTaskViewHolder> {
    private List<LocalDate> yearDays;
    private OnItemClickListener listener;
    private SQLiteHelper db;

    public UpcomingTaskAdapter(List<LocalDate> yearDays, OnItemClickListener listener) {
        this.yearDays = yearDays;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UpcomingTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_upcoming_task, parent, false);

        return new UpcomingTaskViewHolder(view, yearDays);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingTaskViewHolder holder, int position) {
        LocalDate date = yearDays.get(position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = date.format(formatter);
        db = new SQLiteHelper(holder.itemView.getContext());
        List<Task> thisDayTask = db.getByDateToday(formattedDate);
        holder.setData(date, thisDayTask);
    }

    @Override
    public int getItemCount() {
        return yearDays.size();
    }

    public interface OnItemClickListener {
        void onItemClick(LocalDate day, int position);
    }
}

