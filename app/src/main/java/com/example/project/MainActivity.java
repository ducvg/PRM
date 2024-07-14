package com.example.project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.project.adapter.CategoryAdapter;
import com.example.project.adapter.ViewPagerAdapter;
import com.example.project.dal.SQLiteHelper;
import com.example.project.model.Category;
import com.example.project.model.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private PopupWindow createWindow;
    private ViewGroup container;
    private LayoutInflater popupInflater;
    private RelativeLayout mainLayout;
    private TextView txtDueDate, txtDueTime;
    private EditText edtTitle, edtDescription;
    private Spinner spnCategory;
    private Button btnCancel, btnCreate;
    private DatePickerDialog datePickerDialog;
    private static LocalDateTime selectDate;
    private TimePickerDialog timePickerDialog;
    private int selectedCategoryId;
    private SQLiteHelper db;


    private void bindDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            selectDate = selectDate.withYear(year)
                    .withMonth(month + 1) // month is 0-based in DatePickerDialog
                    .withDayOfMonth(day);
            updateDueTV();
        };

        datePickerDialog = new DatePickerDialog(this,
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
        timePickerDialog = new TimePickerDialog(this,
                timeSetLisener,
                selectDate.getHour(), selectDate.getMinute(),true);
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

    private void updateDueTV() {
        txtDueDate.setText(selectDate.toLocalDate().toString());
        txtDueTime.setText(String.format("%02d:%02d", selectDate.getHour(), selectDate.getMinute()));
    }

    private void bindingView() {
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        mainLayout = findViewById(R.id.main);
        fab = findViewById(R.id.fab);
        selectDate = LocalDateTime.now();
        bindDatePicker();
        bindTimePicker();
        bindPopupWindow();
        txtDueDate = container.findViewById(R.id.txtDueDate);
        txtDueTime = container.findViewById(R.id.txtDueTime);
        edtTitle = container.findViewById(R.id.edtitle);
        edtDescription = container.findViewById(R.id.edtAddDescription);
        spnCategory = container.findViewById(R.id.spnCategory);
        btnCreate = container.findViewById(R.id.btnCreate);
        btnCancel = container.findViewById(R.id.btnCancel);
        loadSpinnerData();

    }

    private void loadSpinnerData() {
        try{
            db = new SQLiteHelper(this);
            List<Category> categories = db.getAllCategory();
            CategoryAdapter adapter = new CategoryAdapter(this,categories);
            spnCategory.setAdapter(adapter);
        }catch (Exception e){
            Log.d("Error Load Spinner Popup Add", e.getMessage());
        }

    }

    private void bindPopupWindow() {
        popupInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        container = (ViewGroup) popupInflater.inflate(R.layout.activity_create_popup, null);
        createWindow = new PopupWindow(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        createWindow.setOutsideTouchable(true);
        createWindow.setTouchable(true);
    }

    private void bindingAction() {
        getApplicationContext();
        fab.setOnClickListener(this::openCreateWindow);
        txtDueDate.setOnClickListener(this::openDatePicker);
        txtDueTime.setOnClickListener(this::openTimePicker);
        btnCancel.setOnClickListener(v -> createWindow.dismiss());
        btnCreate.setOnClickListener(this::addTask);


    }

    private void addTask(View view) {
        String title = edtTitle.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String Duedate = txtDueDate.getText().toString().trim();
        String DueTime = txtDueTime.getText().toString().trim();
        int categoryId;

        // Kiểm tra xem Spinner có phần tử nào không
        if (spnCategory.getSelectedItem() == null) {
            Toast.makeText(this, "Danh mục không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            categoryId = ((Category) spnCategory.getSelectedItem()).getCategoryId();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fullDueDateTimeString =  Duedate + " " + DueTime;
        Date dueDate = null;
        try {
            // Phân tích chuỗi ngày giờ
             dueDate = dateFormat.parse(fullDueDateTimeString);
        } catch (ParseException e) {
            Toast.makeText(this, "Ngày giờ không hợp lệ", Toast.LENGTH_SHORT).show();
            Log.e("Parse Error", "Error parsing date: " + e.getMessage());
            return;
        }

        try {
            Task newTask = new Task(title, description, dueDate, categoryId);
            Log.e("Task dyi", "Task title: " + title + " Description: " + description + " Due Date: " + dueDate + " Category ID: " + categoryId);
            long result = db.addTask(newTask);

        } catch (Exception e) {
            Log.e("Add Task Error", "Error adding task: " + e.getMessage());
            Toast.makeText(this, "Đã xảy ra lỗi khi thêm công việc", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCreateWindow(View view) {
        View background = new View(this);
        background.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        background.setBackgroundColor(Color.parseColor("#88000000")); // Màu xám mờ
        mainLayout.addView(background);

        // Làm mờ nền phía sau
        background.setAlpha(0.5f);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setVisibility(View.GONE);


        createWindow.setOnDismissListener(() -> {
            // Khôi phục nền khi popup bị đóng
            mainLayout.removeView(background);
            bottomNav.setVisibility(View.VISIBLE);
        });
        createWindow.showAtLocation(mainLayout, Gravity.CENTER,0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.mToday).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mUpcoming).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.mProfile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        seedData();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.mToday){
                    viewPager.setCurrentItem(0);
                }else if (id == R.id.mUpcoming){
                    viewPager.setCurrentItem(1);
                }else if(id == R.id.mSearch){
                    viewPager.setCurrentItem(2);
                }else if(id == R.id.mProfile){
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }

    private void seedData() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}