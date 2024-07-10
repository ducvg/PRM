package com.example.project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.adapter.RecyclerViewAdapter;
import com.example.project.dal.SQLiteHelper;
import com.example.project.model.Category;
import com.example.project.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentToday extends Fragment implements RecyclerViewAdapter.TaskListenr {
    RecyclerViewAdapter adapter,adapterOverdue;
    private RecyclerView recyclerViewToday,recyclerViewOverdue;
    private SQLiteHelper db;
    private TextView tvTodayDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewToday = view.findViewById(R.id.recycleViewToday);
        recyclerViewOverdue = view.findViewById(R.id.recycleViewOverDue);
        tvTodayDate = view.findViewById(R.id.tvTodayDate);
        adapter = new RecyclerViewAdapter();
        adapterOverdue = new RecyclerViewAdapter();
        db = new SQLiteHelper(getContext());
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String todayDateStr = dayFormat.format(new Date());
        tvTodayDate.setText(todayDateStr);
        List<Task> list = db.getByDateToday(todayDateStr);
        List<Task> listOverDue = db.getOverDue();
        Log.d("FragmentToday", "Number of tasks retrieved: " + list.size());
        Log.d("FragmentToday", "Number of tasks retrieved: " + listOverDue.size());
        adapter.setList(list);
        adapterOverdue.setList(listOverDue);

        LinearLayoutManager manager1 = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerViewToday.setLayoutManager(manager1);
        recyclerViewOverdue.setLayoutManager(manager2);
        recyclerViewToday.setAdapter(adapter);
        recyclerViewOverdue.setAdapter(adapterOverdue);
    }


    @Override
    public void onTaskClick(View view, int position) {

    }
}
