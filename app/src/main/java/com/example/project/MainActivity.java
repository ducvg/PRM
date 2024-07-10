package com.example.project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.project.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private PopupWindow createWindow;
    private ViewGroup container;
    private LayoutInflater popupÌnlater;
    private RelativeLayout mainLayout;
    private TextView txtDueDate, txtDueTime;
    private EditText edtTitle, edtDescription;
    private Spinner spnCategory;
    private Button btnCancel, btnCreate;
    private DatePickerDialog datePickerDialog;
    private static LocalDateTime selectDate;
    private TimePickerDialog timePickerDialog;

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
    }

    private void bindPopupWindow() {
        popupÌnlater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        container = (ViewGroup) popupÌnlater.inflate(R.layout.activity_create_popup, null);
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

    }

    private void openCreateWindow(View view) {
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
}