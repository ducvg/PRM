package com.example.project.fragment.Today;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.dal.SQLiteHelper;
import com.example.project.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodayTaskAdapter extends RecyclerView.Adapter<TodayTaskViewHolder> {
    private List<String> recentDate;
    private SQLiteHelper db;


    public TodayTaskAdapter(List<String> recentDate) {
        this.recentDate = recentDate;
    }

    @NonNull
    @Override
    public TodayTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_task, parent, false);
        db = new SQLiteHelper(parent.getContext());
        return new TodayTaskViewHolder(view, recentDate);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTaskViewHolder holder, int position) {
        String date = recentDate.get(position);
        List<Task> thisDayTask;

        switch (date) {
            case "Overdue":
                thisDayTask = db.getOverDue();
                break;
            case "Today":
                String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                thisDayTask = db.getByDateToday(formattedDate);
                break;
            case "Upcoming":
                thisDayTask = db.getUpcomingTask();
                break;
            default:
                thisDayTask = new ArrayList<>();
                break;
        }
        Log.d("debug today adapter", Arrays.toString(thisDayTask.toArray()));
        holder.setData(date,thisDayTask);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}