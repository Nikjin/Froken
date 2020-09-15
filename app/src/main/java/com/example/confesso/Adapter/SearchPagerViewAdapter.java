package com.example.confesso.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.confesso.Fragment.SanonymousFragment;
import com.example.confesso.Fragment.SknownFragment;

public class SearchPagerViewAdapter extends FragmentPagerAdapter {
    public SearchPagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                SknownFragment sknownFragment = new SknownFragment();
                return sknownFragment;

            case 1:
                SanonymousFragment sanonymousFragment = new SanonymousFragment();
                return sanonymousFragment;

            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
