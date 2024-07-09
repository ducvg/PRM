package com.example.project.fragment;

import android.app.DatePickerDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.CalendarUtils;
import com.example.project.R;
import com.example.project.Service.ApiService;
import com.example.project.Upcoming.UpcomingCalendarAdapter;
import com.example.project.Upcoming.UpcomingTaskAdapter;
import com.example.project.model.ListResponse;
import com.example.project.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentUpcoming extends Fragment implements UpcomingCalendarAdapter.OnItemClickListener, UpcomingTaskAdapter.OnItemClickListener {
    private DatePickerDialog datePickerDialog;
    private Button btnPrevious;
    private Button btnNext;
    private Button btnMonthYear;
    private RecyclerView rcvCalendar;
    private RecyclerView rcvTask;
    private List<Task> dayTask = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarUtils.todayDateTime = LocalDate.now().atStartOfDay();
        CalendarUtils.selectDate = LocalDate.now().atStartOfDay();
        bindingView(view);
        bindingAction();
        init();
    }

    private void init() {
        setWeekView();
        bindDatePicker();
        initTaskList();
    }

    private void initTaskList() {
        List<LocalDate> days = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDateFirstMonth = startDate.plusMonths(1);
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDateFirstMonth)) {
            days.add(currentDate);
            getTasksInDate(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        UpcomingTaskAdapter taskAdapter = new UpcomingTaskAdapter(days, dayTask, this);
        rcvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvTask.setAdapter(taskAdapter);
    }

    private void getTasksInDate(LocalDate date) {
        try {
            ApiService.getTaskApiEndpoint()
                    .getTask()
                    .enqueue(new Callback<ListResponse<Task>>() {
                        @Override
                        public void onResponse(Call<ListResponse<Task>> call, Response<ListResponse<Task>> response) {
                            if (response.body() != null) {
                                Log.d("debug fragment api ok", "api ok");
                                dayTask.addAll(response.body().getData());
                                rcvTask.getAdapter().notifyDataSetChanged(); // Notify adapter when data changes
                            } else {
                                Log.d("debug fragment api ok", "response body is null");
                            }
                        }

                        @Override
                        public void onFailure(Call<ListResponse<Task>> call, Throwable t) {
                            Log.d("debug fragment api fail", t.getMessage() + " || " + call.request());
                        }
                    });
        } catch (Exception e) {
            Log.d("debug fragment api exception", e.getMessage());
        }
    }

    private void bindingView(View view) {
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnMonthYear = view.findViewById(R.id.btnMonthYear);
        rcvCalendar = view.findViewById(R.id.rcvCalendar);
        rcvTask = view.findViewById(R.id.rcvUpcomingTask);
    }

    private void bindDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            CalendarUtils.selectDate = LocalDate.of(year, month + 1, day).atStartOfDay();
            setWeekView();
        };

        datePickerDialog = new DatePickerDialog(requireContext(),
                android.R.style.Theme_Material_Light_Dialog,
                dateSetListener, CalendarUtils.selectDate.getYear(), CalendarUtils.selectDate.getMonthValue() - 1, CalendarUtils.selectDate.getDayOfMonth());
    }

    private void setWeekView() {
        btnMonthYear.setText(CalendarUtils.selectDate.getMonth().toString() + " " + CalendarUtils.selectDate.getYear());

        List<LocalDate> days = CalendarUtils.daysInWeekArray(CalendarUtils.selectDate);

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
        try {
            datePickerDialog.show();
            datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#008374"));
            datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#008374"));
            datePickerDialog.getActionBar().hide();
        } catch (Exception e) {
            Log.d("opendaypicker", e.getMessage());
        }
    }

    private void nextWeek(View view) {
        CalendarUtils.selectDate = CalendarUtils.selectDate.plusWeeks(1);
        setWeekView();
        initTaskList();
    }

    private void prevWeek(View view) {
        CalendarUtils.selectDate = CalendarUtils.selectDate.minusWeeks(1);
        setWeekView();
        initTaskList();
    }

    @Override
    public void onItemClick(LocalDate day, int position) {
        CalendarUtils.selectDate = day.atStartOfDay();
        setWeekView();
        initTaskList();
    }
}
