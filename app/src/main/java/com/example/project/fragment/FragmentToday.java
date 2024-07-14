package com.example.project.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.fragment.Today.TodayTaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentToday extends Fragment {
    private TodayTaskAdapter taskAdapter;
    private RecyclerView rcvRecentDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        List<String> days = new ArrayList<>();
        days.add("Overdue");days.add("Today");days.add("Upcoming");
        taskAdapter = new TodayTaskAdapter(days);
        rcvRecentDay.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvRecentDay.setAdapter(taskAdapter);
    }

    private void bindingView() {
        rcvRecentDay = getView().findViewById(R.id.rcvRecentDay);
    }

//    @Override
//    public void onTaskClick(View view, int position) {
//        Task task = adapter.getTask(position);
//        Intent intent = new Intent(getActivity(), UpdateEditActivity.class);
//        intent.putExtra("task",task);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onTaskStatusChange(Task task, boolean isChecked) {
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> days = new ArrayList<>();
        days.add("Overdue");days.add("Today");days.add("Upcoming");
        taskAdapter = new TodayTaskAdapter(days);
        rcvRecentDay.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvRecentDay.setAdapter(taskAdapter);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
