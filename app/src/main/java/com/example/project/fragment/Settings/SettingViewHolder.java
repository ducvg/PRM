package com.example.project.fragment.Settings;

import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.main.AppConfig;

import java.util.List;

public class SettingViewHolder extends RecyclerView.ViewHolder{
    private List<TextView> settings;
    private LinearLayout lnlSetting;


    public SettingViewHolder(@NonNull View itemView) {
        super(itemView);
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

        }

    }

    private void seedOnlineSetting() {
        TextView sync = new TextView(itemView.getContext());
        sync.setText("Synchronize");
        sync.setOnClickListener(c -> syncData());
        settings.add(sync);
        TextView logout = new TextView(itemView.getContext());
        logout.setText("Synchronize");
        logout.setOnClickListener(c -> logout());
        settings.add(logout);
    }

    private void logout() {

    }

    private void syncData() {
        Toast.makeText(itemView.getContext(), "Synchronizing ...",Toast.LENGTH_LONG).show();
    }

    private void seedOfflineSetting() {

    }

}
