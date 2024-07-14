package com.example.project.fragment.Upcoming;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpcomingTaskViewHolder extends RecyclerView.ViewHolder  {
    private List<LocalDate> days;
    private TextView txtTaskDate,txtTaskDueDate,txtTaskDueTime;
    private LinearLayout lnlTaskList;

    //item
    private TextView txtTitle;
    private CheckBox cbTodo;
    private TextView txtDescription;
    private TextView txtDue;
    private TextView txtCategory;
    //item

    private EditText etTaskTitle,etTaskDescription;
    private Spinner spnTaskCategory;
    private SQLiteHelper db;
    private Button btnCancel,btnUpdate;
    private AlertDialog alertDialog;
    private DatePickerDialog datePickerDialog;
    private static LocalDateTime selectDate;
    private TimePickerDialog timePickerDialog;
    private View popupView;

    public UpcomingTaskViewHolder(@NonNull View itemView, List<LocalDate> days) {
        super(itemView);
        this.days = days;
        selectDate = LocalDateTime.now();
        bindPopup();
        bindingView();
        bindDatePicker();
        bindTimePicker();
        bindingItemView(itemView);
        db = new SQLiteHelper(itemView.getContext());
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
        etTaskTitle = popupView.findViewById(R.id.edtitle);
        etTaskDescription = popupView.findViewById(R.id.edtAddDescription);
        spnTaskCategory = popupView.findViewById(R.id.spnCategory);
        txtTaskDueDate = popupView.findViewById(R.id.txtDueDate);
        txtTaskDueTime = popupView.findViewById(R.id.txtDueTime);
    }

    private void bindingItemAction(Task t) {
        cbTodo.setOnCheckedChangeListener(this::checkStatus);
        txtTitle.setOnClickListener(v -> showEditDialog(t));
        txtDescription.setOnClickListener(v -> showEditDialog(t));
    }

    private void bindPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
        popupView = inflater.inflate(R.layout.activity_update_edit, null);
        builder.setView(popupView);
        alertDialog = builder.create();
    }
    private void bindDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            selectDate = selectDate.withYear(year)
                    .withMonth(month + 1)
                    .withDayOfMonth(day);
            updateDueTV();
        };

        datePickerDialog = new DatePickerDialog(itemView.getContext(),
                android.R.style.Theme_Material_Light_Dialog,
                dateSetListener,
                selectDate.getYear(), selectDate.getMonthValue() - 1, selectDate.getDayOfMonth());
    }

    private void bindTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetLisener =  (view, hourOfDay, minute) -> {
            selectDate = selectDate.withHour(hourOfDay)
                    .withMinute(minute);
            Log.d("debug timepick",selectDate.toString());
            updateDueTV();
        };
        timePickerDialog = new TimePickerDialog(itemView.getContext(),
                timeSetLisener,
                selectDate.getHour(), selectDate.getMinute(),true);
    }

    private void updateDueTV() {
        txtTaskDueDate.setText(String.format("%02d-%02d-%d", selectDate.getMonthValue(), selectDate.getDayOfMonth(), selectDate.getYear()));
        txtTaskDueTime.setText(String.format("%02d:%02d", selectDate.getHour(), selectDate.getMinute()));
    }

    private void checkStatus(CompoundButton compoundButton, boolean b) {

    }

    public void setData(LocalDate date, List<Task> thisDayTask){
        String monthFullName = date.getMonth().toString();
        monthFullName = monthFullName.substring(0, 1).toUpperCase() + monthFullName.substring(1).toLowerCase();
        String dayofweek = date.getDayOfWeek().toString();
        dayofweek = dayofweek.substring(0, 1).toUpperCase() + dayofweek.substring(1).toLowerCase();
        txtTaskDate.setText(monthFullName+" "+date.getDayOfMonth()+" "+date.getYear()+" | "+dayofweek);

        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
        lnlTaskList.removeAllViews();
        for(Task t : thisDayTask){
            View view = inflater.inflate(R.layout.task, lnlTaskList, false);
            bindingItemView(view);
            bindingItemAction(t);
            txtTitle.setText(t.getTitle());
            txtDescription.setText(t.getDescription());
            txtDue.setText(t.getDueDate().toString());
            SQLiteHelper db = new SQLiteHelper(itemView.getContext());
            String categoryName = db.getCategoryNameById(t.getCategoryId());
            txtCategory.setText(categoryName);
            if(t.getStatus()==0){
                cbTodo.setChecked(false);
            }else if(t.getStatus()==1){
                cbTodo.setChecked(true);
            }
            lnlTaskList.addView(view);
        }


    }
    private void showEditDialog(Task task) {


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

