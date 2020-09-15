package com.example.confesso.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.confesso.Fragment.HanonymousFragment;
import com.example.confesso.Fragment.HknownFragment;
import com.example.confesso.Fragment.HsharedFragment;

public class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

   //idhar future mei new constructor ka use krke setuservisiblehint method ko setmaxlifecycle method se replace krna hai

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                HknownFragment hknownFragment = new HknownFragment();
                return hknownFragment;

            case 1:
                HanonymousFragment hanonymousFragment = new HanonymousFragment();
                return hanonymousFragment;

            case 2:
                HsharedFragment hsharedFragment = new HsharedFragment();
                return hsharedFragment;

            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return 3;
    }




}
