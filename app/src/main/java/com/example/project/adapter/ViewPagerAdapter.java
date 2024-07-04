package com.example.project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project.fragment.FragmentProfile;
import com.example.project.fragment.FragmentSearch;
import com.example.project.fragment.FragmentToday;
import com.example.project.fragment.FragmentUpcoming;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentToday();
            case 1: return new FragmentUpcoming();
            case 2: return new FragmentSearch();
            case 3: return new FragmentProfile();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
