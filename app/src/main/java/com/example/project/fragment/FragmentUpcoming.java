package com.example.project.fragment;

import static com.example.project.CalendarUtils.daysInWeekArray;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.CalendarUtils;
import com.example.project.R;
import com.example.project.Upcoming.UpcomingCalendarAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FragmentUpcoming extends Fragment implements UpcomingCalendarAdapter.OnItemClickListener {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button btnPrevious;
    private Button btnNext;
    private Button btnMonthYear;
    private RecyclerView rcvCalendar;
    private RecyclerView rcvTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarUtils.todayDateTime = LocalDateTime.now();
        CalendarUtils.selectDate = LocalDateTime.now();
        bindingView();
        init();
        bindingAction();
    }

    private void init() {
        setWeekView();
        bindDatePicker();
        bindTimePicker();
        initDayTask();
    }

    private void initDayTask() {

    }

    private void bindingView() {
        btnPrevious = getView().findViewById(R.id.btnPrevious);
        btnNext = getView().findViewById(R.id.btnNext);
        btnMonthYear = getView().findViewById(R.id.btnMonthYear);
        rcvCalendar = getView().findViewById(R.id.rcvCalendar);
        rcvTask = getView().findViewById(R.id.rcvTask);
    }

    private void bindDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            CalendarUtils.selectDate = LocalDateTime.of(year,month,day,0,0,0);
            setWeekView();
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(),
                android.R.style.Theme_Material_Light_Dialog,
                dateSetListener, year, month, day);
    }

    private void bindTimePicker() {
//        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hour, minute) -> {
//
//        };
//        timePickerDialog = new TimePickerDialog(getActivity(),
//                android.R.style.Theme_Material_Light_Dialog,
//                timeSetListener, CalendarUtils.selectDate.getHour(), CalendarUtils.selectDate.getMinute(),true);
    }

    private void setWeekView() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US);
        String date =  CalendarUtils.selectDate.format(formatter);
        btnMonthYear.setText(date);

        List<LocalDate> days = daysInWeekArray(CalendarUtils.selectDate);

        UpcomingCalendarAdapter calendarAdapter = new UpcomingCalendarAdapter(days, this);
        rcvCalendar.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        rcvCalendar.setAdapter(calendarAdapter);
    }

    private void bindingAction() {
        btnPrevious.setOnClickListener(this::prevWeek);
        btnNext.setOnClickListener(this::nextWeek);
        btnMonthYear.setOnClickListener(this::openDatePicker);
    }

    private void openDatePicker(View view) {
        try{
            datePickerDialog.show();
            datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#008374"));
            datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#008374"));

            datePickerDialog.getActionBar().hide();
        } catch (Exception e){
            Log.d("opendaypicker", e.getMessage());
        }
    }

    private void nextWeek(View view) {
        CalendarUtils.selectDate = CalendarUtils.selectDate.plusWeeks(1);
        setWeekView();
    }

    private void prevWeek(View view) {
        CalendarUtils.selectDate = CalendarUtils.selectDate.minusWeeks(1);
        setWeekView();
    }

    public void prevMonth(View view) {
        Toast.makeText(getActivity(), "Your answer is correct!" , Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onItemClick(LocalDate day, int position) {
        CalendarUtils.selectDate = LocalDateTime.from(day.atStartOfDay());
        setWeekView();
    }
}
