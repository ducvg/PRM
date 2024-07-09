package com.example.project.Upcoming;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.CalendarUtils;
import com.example.project.R;

import java.time.LocalDate;
import java.util.List;

public class UpcomingCalendarAdapter extends RecyclerView.Adapter<UpcomingCalendarViewHolder>{
    private List<LocalDate> weekDay;
    private OnItemClickListener listener;

    public UpcomingCalendarAdapter(List<LocalDate> weekDay, OnItemClickListener listener) {
        this.weekDay = weekDay;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UpcomingCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_upcoming_calendar_item, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = parent.getHeight();
        return new UpcomingCalendarViewHolder(view, weekDay, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingCalendarViewHolder holder, int position) {
        LocalDate date = weekDay.get(position);
        holder.setData(String.valueOf(date.getDayOfMonth()));
        if(date.equals(CalendarUtils.todayDateTime.toLocalDate())){
            holder.getTxtDay().setBackgroundResource(R.drawable.bg_today);
            holder.getTxtDay().setTextColor(Color.WHITE);
        } else if(date.equals(CalendarUtils.selectDate.toLocalDate())){
            holder.getTxtDay().setBackgroundResource(R.drawable.bg_selected_day);
            holder.getTxtDay().setTextColor(Color.BLACK);
        } else {
            holder.getTxtDay().setBackground(null);
            holder.getTxtDay().setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return weekDay.size();
    }

    public interface OnItemClickListener {
        void onItemClick(LocalDate day, int position);
    }
}
