package com.example.project.fragment.Today;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.time.LocalDateTime;
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
    private DatePickerDialog datePickerDialog;
    private static LocalDateTime selectDate;
    private TimePickerDialog timePickerDialog;



    public TodayTaskViewHolder(@NonNull View itemView) {
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
            String categoryName = db.getCategoryNameById(t.getCategoryId());
            txtCategory.setText(categoryName);
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
        etTaskTitle = popupView.findViewById(R.id.edtitle);
        etTaskDescription = popupView.findViewById(R.id.edtAddDescription);
        spnTaskCategory = popupView.findViewById(R.id.spnCategory);
        txtTaskDueDate = popupView.findViewById(R.id.txtDueDate);
        txtTaskDueTime = popupView.findViewById(R.id.txtDueTime);


        txtTaskDueDate.setOnClickListener(this::openDatePicker);
        txtTaskDueTime.setOnClickListener(this::openTimePicker);

        // Lấy danh sách Category từ SQLiteHelper
        List<Category> categoryList = db.getAllCategory();

        // Tạo Adapter cho Spinner
        CategoryAdapter adapter = new CategoryAdapter(itemView.getContext(), categoryList);
        spnTaskCategory.setAdapter(adapter);

        // Đặt giá trị từ task vào các view
        etTaskTitle.setText(task.getTitle());
        etTaskDescription.setText(task.getDescription());
        spnTaskCategory.setSelection(task.getCategoryId()-1);
        Date dueDate = task.getDueDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String formattedDueDate = dateFormat.format(dueDate);
        txtTaskDueDate.setText(formattedDueDate);
        try{
            SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String formattedDueDate2 = TimeFormat.format(dueDate);
            txtTaskDueTime.setText(formattedDueDate2); // Tương tự với thời gian
        }catch (Exception e){
            Log.d("erorrrrrrr",e.getMessage());
        }



        Button btnUpdate = popupView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedCategoryId = ((Category) spnTaskCategory.getSelectedItem()).getCategoryId();

                // Lấy lại giá trị ngày giờ từ TextViews txtDueDate và txtDueTime
                String dueDateString = txtTaskDueDate.getText().toString();
                String dueTimeString = txtTaskDueTime.getText().toString();
                String fullDueDateTimeString = dueDateString + " " + dueTimeString;

                // Chuyển đổi định dạng ngày giờ sang Date object
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());
                Date updatedDueDate = null;
                try {
                    updatedDueDate = sdf.parse(fullDueDateTimeString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                task.setTitle(etTaskTitle.getText().toString());
                task.setDescription(etTaskDescription.getText().toString());
                task.setDueDate(updatedDueDate);
                task.setCategoryId(updatedCategoryId);
                db.updateTask(task);
                Log.d("day la task update", "category id = " + updatedCategoryId);
                Toast.makeText(itemView.getContext(), "Đã cập nhật công việc", Toast.LENGTH_SHORT).show();
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

    private void openTimePicker(View view) {
        try {
            timePickerDialog.show();
            timePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#008374"));
            timePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#008374"));
            timePickerDialog.getActionBar().hide();
        } catch (Exception e) {
            Log.d("opendaypicker", e.getMessage());
        }
    }

    private void openDatePicker(View view) {
        try {
            datePickerDialog.show();
            datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#008374"));
            datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#008374"));
            datePickerDialog.getActionBar().hide();
        } catch (Exception e) {
            Log.d("opendaypicker", e.getMessage());
        }
    }


    private void checkStatus(CompoundButton compoundButton, boolean b) {


    }



    @Override
    public void onClick(View view) {

    }
}