package com.example.confesso.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.confesso.Fragment.BanonymousFragment;
import com.example.confesso.Fragment.BknownFragment;

public class SharedPagerViewAdapter extends FragmentPagerAdapter {
    public SharedPagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                BknownFragment bknownFragment = new BknownFragment();
                return bknownFragment;

            case 1:
                BanonymousFragment banonymousFragment = new BanonymousFragment();
                return banonymousFragment;

            default:
                return null;


        }    }

    @Override
    public int getCount() {
        return 2;
    }
}
