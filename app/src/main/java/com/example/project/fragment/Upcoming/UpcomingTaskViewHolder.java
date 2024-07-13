package com.example.project.fragment.Upcoming;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.dal.SQLiteHelper;
import com.example.project.model.Task;

import java.time.LocalDate;
import java.util.List;

public class UpcomingTaskViewHolder extends RecyclerView.ViewHolder  {
    private List<LocalDate> days;
    private TextView txtTaskDate;
    private LinearLayout lnlTaskList;

    //item
    private TextView txtTitle;
    private CheckBox cbTodo;
    private TextView txtDescription;
    private TextView txtDue;
    private TextView txtCategory;
    //item

    public UpcomingTaskViewHolder(@NonNull View itemView, List<LocalDate> days) {
        super(itemView);
        this.days = days;
        bindingView();
    }

    private void bindingView() {
        txtTaskDate = itemView.findViewById(R.id.txtTaskDate);
        lnlTaskList = itemView.findViewById(R.id.lnlTaskList);
    }

    private void bindingItemView(View view){
        txtTitle = view.findViewById(R.id.todoTitle);
        cbTodo = view.findViewById(R.id.todoCheckBox);
        txtDescription = view.findViewById(R.id.todoDescription);
        txtDue = view.findViewById(R.id.todoDateTime);
        txtCategory = view.findViewById(R.id.todoCategory);
    }

    private void bindingItemAction() {
        cbTodo.setOnCheckedChangeListener(this::checkStatus);
    }

    private void checkStatus(CompoundButton compoundButton, boolean b) {
        Toast.makeText(itemView.getContext(),"item check", Toast.LENGTH_SHORT).show();
    }

    public void setData(LocalDate date, List<Task> thisDayTask){
        String monthFullName = date.getMonth().toString();
        monthFullName = monthFullName.substring(0, 1).toUpperCase() + monthFullName.substring(1);
        txtTaskDate.setText(monthFullName+" "+date.getDayOfMonth()+" "+date.getYear()+" | "+date.getDayOfWeek());

        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
        lnlTaskList.removeAllViews();
        for(Task t : thisDayTask){
            View view = inflater.inflate(R.layout.task, lnlTaskList, false);
            bindingItemView(view);
            bindingItemAction();

            txtTitle.setText(t.getTitle());
            txtDescription.setText(t.getDescription());
            txtDue.setText(t.getDueDate().toString());
            txtCategory.setText(String.valueOf(t.getCategoryId()));
            lnlTaskList.addView(view);
        }


    }



    public LinearLayout getLnlTaskList() {
        return lnlTaskList;
    }

    public void setLnlTaskList(LinearLayout lnlTaskList) {
        this.lnlTaskList = lnlTaskList;
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

}

