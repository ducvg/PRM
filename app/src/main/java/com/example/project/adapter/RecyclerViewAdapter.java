//package com.example.project.adapter;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.project.R;
//import com.example.project.model.Task;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TodayViewHolder> {
//    private List<Task> list;
//    private TaskListenr taskListenr;
//
//    public RecyclerViewAdapter() {
//        list = new ArrayList<>();
//    }
//
//    public void setTaskListenr(TaskListenr taskListenr) {
//        this.taskListenr = taskListenr;
//    }
//
//    public void setList(List<Task> list){
//        this.list = list;
//        notifyDataSetChanged();
//    }
//    public Task getTask(int position){
//        return list.get(position);
//    }
//
//    @NonNull
//    @Override
//    public TodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task,parent,false);
//        return new TodayViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TodayViewHolder holder, int position) {
//        Task task = list.get(position);
//        holder.title.setText(task.getTitle());
//        holder.desc.setText(task.getDescription());
//        Date duedate = task.getDueDate();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String dateString = dateFormat.format(duedate);
//        holder.date.setText(dateString);
//        holder.category.setText(String.valueOf(task.getCategoryId()));
//
//        holder.cbIsCheck.setChecked(task.getStatus() == 1);
//
//        // Handle checkbox state change
//        holder.cbIsCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            task.setStatus(isChecked ? 1 : 0);
//            if (taskListenr != null) {
//                taskListenr.onTaskStatusChange(task, isChecked);
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        private TextView title,desc,date,category,todaydate;
//        private CheckBox cbIsCheck;
//
//        private void bindingView() {
//            title = itemView.findViewById(R.id.todoTitle);
//            desc = itemView.findViewById(R.id.todoDescription);
//            date = itemView.findViewById(R.id.todoDateTime);
//            todaydate =itemView.findViewById(R.id.tvTodayDate);
//            category = itemView.findViewById(R.id.todoCategory);
//            cbIsCheck = itemView.findViewById(R.id.todoCheckBox);
//            itemView.setOnClickListener(this);
//        }
//
//        public TodayViewHolder(@NonNull View itemView) {
//            super(itemView);
//            bindingView();
//
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            if(taskListenr!=null){
//                taskListenr.onTaskClick(view,getAdapterPosition());
//            }
//        }
//    }
//    public interface TaskListenr{
//        void onTaskClick(View view, int position);
//        void onTaskStatusChange(Task task, boolean isChecked);
//    }
//}