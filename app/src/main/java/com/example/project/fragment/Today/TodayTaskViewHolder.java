package com.example.project.fragment.Today;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;

import java.time.LocalDate;
import java.util.List;

public class TodayTaskViewHolder extends RecyclerView.ViewHolder {
    private TextView txtTaskDate;
    private LinearLayout lnlTaskList;

    //item
    private TextView txtTitle;
    private CheckBox cbTodo;
    private TextView txtDescription;
    private TextView txtDue;
    private TextView txtCategory;

    public TodayTaskViewHolder(@NonNull View itemView, List<String> days) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
    }

    private void bindingView() {
        txtTaskDate = itemView.findViewById(R.id.txtTaskDate);
        lnlTaskList = itemView.findViewById(R.id.lnlTaskList);
    }

    public void setData(String date, List<Task> thisDayTask) {
        txtTaskDate.setText(date);

        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
        lnlTaskList.removeAllViews();
        for (Task t : thisDayTask) {
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

        private void bindingItemAction () {
            cbTodo.setOnCheckedChangeListener(this::checkStatus);
        }

    private void checkStatus(CompoundButton compoundButton, boolean b) {
        Toast.makeText(itemView.getContext(),"item check: "+b, Toast.LENGTH_SHORT).show();
    }

    private void bindingItemView (View view){
            txtTitle = view.findViewById(R.id.todoTitle);
            cbTodo = view.findViewById(R.id.todoCheckBox);
            txtDescription = view.findViewById(R.id.todoDescription);
            txtDue = view.findViewById(R.id.todoDateTime);
            txtCategory = view.findViewById(R.id.todoCategory);
    }
}