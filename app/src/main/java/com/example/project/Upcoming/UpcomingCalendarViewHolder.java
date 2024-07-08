package com.example.project.Upcoming;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.time.LocalDate;
import java.util.List;

public class UpcomingCalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View parentView;
    private List<LocalDate> days;
    private TextView txtDay;
    private UpcomingCalendarAdapter.OnItemClickListener listener;

    public UpcomingCalendarViewHolder(@NonNull View itemView, List<LocalDate> days, UpcomingCalendarAdapter.OnItemClickListener listener) {
        super(itemView);
        this.days = days;
        this.listener = listener;
        itemView.setOnClickListener(this);
        bindingView();
        bindingAction();
    }

    private void bindingView() {
        parentView = itemView.findViewById(R.id.vUpcomingItem);
        txtDay = itemView.findViewById(R.id.txtDay);
    }

    private void bindingAction() {

    }

    public void setData(String day) {
        txtDay.setText(day);
    }

    public View getParentView() {
        return parentView;
    }

    public void setParentView(View parentView) {
        this.parentView = parentView;
    }

    public List<LocalDate> getDays() {
        return days;
    }

    public void setDays(List<LocalDate> days) {
        this.days = days;
    }

    public TextView getTxtDay() {
        return txtDay;
    }

    public void setTxtDay(TextView txtDay) {
        this.txtDay = txtDay;
    }

    public UpcomingCalendarAdapter.OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(UpcomingCalendarAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(days.get(getAdapterPosition()), getAdapterPosition());
    }
}
