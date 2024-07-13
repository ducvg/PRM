package com.example.project.fragment.Today;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.adapter.CategoryAdapter;
import com.example.project.dal.SQLiteHelper;
import com.example.project.model.Category;
import com.example.project.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayTaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtTaskDate,txtTaskDueDate,txtTaskDueTime;
    private LinearLayout lnlTaskList;
    private TextView txtTitle;
    private CheckBox cbTodo;
    private TextView txtDescription;
    private TextView txtDue;
    private TextView txtCategory;
    private EditText etTaskTitle,etTaskDescription;
    private Spinner spnTaskCategory;
    private SQLiteHelper db;;
    private Button btnCancel,btnUpdate;
    private AlertDialog alertDialog;


    public TodayTaskViewHolder(@NonNull View itemView, List<String> days) {
        super(itemView);
        bindingView();
        bindingAction();
        db = new SQLiteHelper(itemView.getContext());
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
            txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Hiển thị popup edit khi người dùng click vào txtTitle
                    if (t != null) {
                        showEditDialog(t);
                    } else {
                        Toast.makeText(itemView.getContext(), "Không có task được chọn", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    private void bindingItemView (View view){
        txtTitle = view.findViewById(R.id.todoTitle);
        cbTodo = view.findViewById(R.id.todoCheckBox);
        txtDescription = view.findViewById(R.id.todoDescription);
        txtDue = view.findViewById(R.id.todoDateTime);
        txtCategory = view.findViewById(R.id.todoCategory);

    }

    private void bindingItemAction () {
            cbTodo.setOnCheckedChangeListener(this::checkStatus);
        }

    private void showEditDialog(Task task) {

        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
        View popupView = inflater.inflate(R.layout.activity_update_edit, null);

        // Khởi tạo các view từ popupView
        EditText etTitle = popupView.findViewById(R.id.edtitle);
        EditText etDescription = popupView.findViewById(R.id.edtAddDescription);
        Spinner spnCategory = popupView.findViewById(R.id.spnCategory);
        TextView txtDueDate = popupView.findViewById(R.id.txtDueDate);
        TextView txtDueTime = popupView.findViewById(R.id.txtDueTime);

        // Lấy danh sách Category từ SQLiteHelper
        List<Category> categoryList = db.getAllCategory();

        // Tạo Adapter cho Spinner
        CategoryAdapter adapter = new CategoryAdapter(itemView.getContext(), categoryList);
        spnCategory.setAdapter(adapter);

        // Đặt giá trị từ task vào các view
        etTitle.setText(task.getTitle());
        etDescription.setText(task.getDescription());
        spnCategory.setSelection(task.getCategoryId());
        Date dueDate = task.getDueDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String formattedDueDate = dateFormat.format(dueDate);
        txtDueDate.setText(formattedDueDate);
        try{
            SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String formattedDueDate2 = TimeFormat.format(dueDate);
            txtDueTime.setText(formattedDueDate2); // Tương tự với thời gian
        }catch (Exception e){
            Log.d("erorrrrrrr",e.getMessage());
        }



        Button btnUpdate = popupView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedCategoryId = ((Category) spnCategory.getSelectedItem()).getCategoryId();

                // Lấy lại giá trị ngày giờ từ TextViews txtDueDate và txtDueTime
                String dueDateString = txtDueDate.getText().toString();
                String dueTimeString = txtDueTime.getText().toString();
                String fullDueDateTimeString = dueDateString + " " + dueTimeString;

                // Chuyển đổi định dạng ngày giờ sang Date object
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());
                Date updatedDueDate = null;
                try {
                    updatedDueDate = sdf.parse(fullDueDateTimeString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                task.setTitle(etTitle.getText().toString());
                task.setDescription(etDescription.getText().toString());
                task.setDueDate(updatedDueDate);
                task.setCategoryId(updatedCategoryId);
                db.updateTask(task);
                Log.d("day la task update", "category id = " + updatedCategoryId);
                // Hiển thị thông báo cập nhật thành công hoặc thất bại
                Toast.makeText(itemView.getContext(), "Đã cập nhật công việc", Toast.LENGTH_SHORT).show();
                // Làm mới giao diện người dùng hoặc thực hiện các hành động cần thiết khác sau khi cập nhật
                alertDialog.dismiss();
            }
        });

        Button btnCancel = popupView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss(); // Giả sử alertDialog là biến toàn cục để có thể đóng popup từ bất kỳ đâu
            }
        });


        // Tạo và hiển thị AlertDialog
        builder.setView(popupView);
        alertDialog = builder.create();
        alertDialog.show();
    }


    private void checkStatus(CompoundButton compoundButton, boolean b) {


    }



    @Override
    public void onClick(View view) {

    }
}