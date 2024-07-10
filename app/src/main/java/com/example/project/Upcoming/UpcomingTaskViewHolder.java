package com.example.project.Upcoming;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.time.LocalDate;
import java.util.List;

public class UpcomingTaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private List<LocalDate> days;
    private TextView txtTaskDate;
    private RecyclerView rcvTaskDate;
    private UpcomingTaskAdapter.OnItemClickListener listener;

    public UpcomingTaskViewHolder(@NonNull View itemView, List<LocalDate> days, UpcomingTaskAdapter.OnItemClickListener listener) {
        super(itemView);
        this.days = days;
        this.listener = listener;
        itemView.setOnClickListener(this);
        bindingView();
    }

    private void bindingView() {
        txtTaskDate = itemView.findViewById(R.id.txtTaskDate);
        rcvTaskDate = itemView.findViewById(R.id.rcvTaskDate);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(days.get(getAdapterPosition()), getAdapterPosition());
    }

    public void setData(LocalDate date){
        String monthFullName = date.getMonth().toString();
        monthFullName = monthFullName.substring(0, 1).toUpperCase() + monthFullName.substring(1);
        txtTaskDate.setText(monthFullName+" "+date.getDayOfMonth()+" | "+date.getDayOfWeek());
    }

    public List<LocalDate> getDays() {
        return days;
    }

    public void setDays(List<LocalDate> days) {
        this.days = days;
    }

    public TextView getTxtTaskDate() {
        return txtTaskDate;
    }

    public void setTxtTaskDate(TextView txtTaskDate) {
        this.txtTaskDate = txtTaskDate;
    }

    public RecyclerView getRcvTaskDate() {
        return rcvTaskDate;
    }

    public void setRcvTaskDate(RecyclerView rcvTaskDate) {
        this.rcvTaskDate = rcvTaskDate;
    }

    public UpcomingTaskAdapter.OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(UpcomingTaskAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

