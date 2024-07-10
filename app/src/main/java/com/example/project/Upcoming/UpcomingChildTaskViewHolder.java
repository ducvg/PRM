package com.example.project.Upcoming;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;

public class UpcomingChildTaskViewHolder extends RecyclerView.ViewHolder{
    private CheckBox cbTodo;
    private UpcomingTaskAdapter.OnItemClickListener listener;
    private TextView txtTitle, txtDescription, txtDueTime, txtCategory;

    public UpcomingChildTaskViewHolder(@NonNull View itemView, UpcomingTaskAdapter.OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        cbTodo.setOnClickListener(this::onCheckComplete);
    }

    private void onCheckComplete(View view) {
    }

    private void bindingView() {
        cbTodo = itemView.findViewById(R.id.todoCheckBox);
        txtTitle = itemView.findViewById(R.id.todoTitle);
        txtDescription = itemView.findViewById(R.id.todoDescription);
        txtDueTime = itemView.findViewById(R.id.todoDateTime);
        txtCategory = itemView.findViewById(R.id.txtCategory);
    }

    

    public void setData(Task task) {
//        cbTodo.setChecked(true);
        Log.d("debug children",task.toString());
        txtTitle.setText(task.getTitle());
        txtDescription.setText(task.getDescription());
        txtDueTime.setText(task.getDueDate().toString());
        txtCategory.setText(task.getCategoryId());
    }
}
