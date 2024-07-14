package com.example.project.fragment.Settings;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.LoginActivity;
import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.Service.ApiService;
import com.example.project.dal.SQLiteHelper;
import com.example.project.main.AppConfig;
import com.example.project.model.Category;
import com.example.project.model.ServiceModel.ListResponse;
import com.example.project.model.ServiceModel.TaskDTO;
import com.example.project.model.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingViewHolder extends RecyclerView.ViewHolder{
    private List<TextView> settings;
    private LinearLayout lnlSetting;
    private SQLiteHelper db;


    public SettingViewHolder(@NonNull View itemView) {
        super(itemView);
        settings = new ArrayList<>();
        db = new SQLiteHelper(itemView.getContext());
        bindingView();
    }

    private void bindingView() {
        lnlSetting = itemView.findViewById(R.id.lnlSetting);
    }

    //<!--        android:foreground="?attr/selectableItemBackgroundBorderless"-->
//<!--        android:padding="15dp"-->
//<!--        android:text="Jul 12 | Friday"-->
//<!--        android:textColor="@color/black"-->
//<!--        android:textSize="20sp"-->
//<!--        android:textStyle="bold"-->
    public void setData() {
        if(AppConfig.isOffline) seedOfflineSetting();
        else seedOnlineSetting();
        TypedValue outValue = new TypedValue();
        itemView.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

        for(TextView setting : settings){
            setting.setClickable(true);
            setting.setBackgroundResource(outValue.resourceId);
            setting.setPadding(15,15,15,15);
            setting.setTextColor(Color.parseColor("#000000"));
            setting.setTextSize(20);
            lnlSetting.addView(setting);
            View divider = new View(itemView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2
            );
            divider.setLayoutParams(params);
            divider.setBackgroundColor(Color.parseColor("#CCCCCC"));
            lnlSetting.addView(divider);
        }

    }

    private void seedOnlineSetting() {
        TextView sync = new TextView(itemView.getContext());
        sync.setText("Synchronize");
        sync.setOnClickListener(c -> syncData());
        settings.add(sync);
        TextView logout = new TextView(itemView.getContext());
        logout.setText("Logout");
        logout.setOnClickListener(c -> logout());
        settings.add(logout);
    }

    private void syncCategory() {
        try {
            ApiService.getCategoryApiEndpoint()
                    .getAllCategory()
                    .enqueue(new Callback<ListResponse<Category>>() {
                        @Override
                        public void onResponse(Call<ListResponse<Category>> call, Response<ListResponse<Category>> response) {
                            List<Category> syncedData = response.body().getData();
                            db.syncCategory(syncedData);
                            Log.d("debug setting sync category ok", "synced");
                        }

                        @Override
                        public void onFailure(Call<ListResponse<Category>> call, Throwable throwable) {
                            Log.d("debug setting sync category fail", throwable.getMessage());
                            Toast.makeText(itemView.getContext(),"Sync setting category fail: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Log.d("debug sync category exception", e.getMessage());
            Toast.makeText(itemView.getContext(),"Sync setting category went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void syncTask(){
        List<TaskDTO> userTasks = db.getAllLocalTask();
        try {
            ApiService.getTaskApiEndpoint()
                    .updateServer(userTasks)
                    .enqueue(new Callback<List<Task>>() {
                        @Override
                        public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                            List<Task> syncedData = response.body();
                            db.syncTask(syncedData);
                            Log.d("debug setting sync api ok", syncedData.toString());
                        }

                        @Override
                        public void onFailure(Call<List<Task>> call, Throwable throwable) {
                            Log.d("debug setting sync api fail", throwable.getMessage());
                            Toast.makeText(itemView.getContext(),"Sync fail: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Log.d("debug setting sync api exception", e.getMessage());
            Toast.makeText(itemView.getContext(),"Sync setting data went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        Toast.makeText(itemView.getContext(), "Logout ...",Toast.LENGTH_LONG).show();
    }

    private void syncData() {
        Toast.makeText(itemView.getContext(), "Synchronizing ...",Toast.LENGTH_LONG).show();
        syncCategory();
        syncTask();
    }

    private void seedOfflineSetting() {

    }

}
