package com.example.project.Upcoming;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

public class UpcomingChildTaskViewHolder extends RecyclerView.ViewHolder{
    private CheckBox cbTodo;
    private TextView txtTitle, txtDescription, txtDueTime, txtCategory;

    public UpcomingChildTaskViewHolder(@NonNull View itemView, CheckBox cbTodo, TextView txtTitle, TextView txtDescription, TextView txtDueTime, TextView txtCategory) {
        super(itemView);
        this.cbTodo = cbTodo;
        this.txtTitle = txtTitle;
        this.txtDescription = txtDescription;
        this.txtDueTime = txtDueTime;
        this.txtCategory = txtCategory;
        bindingView();
    }

    private void bindingView() {
        cbTodo = itemView.findViewById(R.id.todoCheckBox);
        txtTitle = itemView.findViewById(R.id.todoTitle);
        txtDescription = itemView.findViewById(R.id.todoDescription);
        txtDueTime = itemView.findViewById(R.id.todoDateTime);
        txtCategory = itemView.findViewById(R.id.txtCategory);
    }

    public CheckBox getCbTodo() {
        return cbTodo;
    }

    public void setCbTodo(CheckBox cbTodo) {
        this.cbTodo = cbTodo;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public TextView getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(TextView txtDescription) {
        this.txtDescription = txtDescription;
    }

    public TextView getTxtDueTime() {
        return txtDueTime;
    }

    public void setTxtDueTime(TextView txtDueTime) {
        this.txtDueTime = txtDueTime;
    }

    public TextView getTxtCategory() {
        return txtCategory;
    }

    public void setTxtCategory(TextView txtCategory) {
        this.txtCategory = txtCategory;
    }
}
