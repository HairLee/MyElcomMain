package com.example.arc.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.arc.view.ui.fragment.TimeKeepingFragment;

import java.util.Date;
import java.util.List;

/**
 * Created by Hailpt on 6/5/2018.
 */
public class TimeKeepingPagerAdapter extends FragmentPagerAdapter {

    private List<List<Date>> mDates;
    private int mCureentPos = 0;
    private int mPrevious= 0;
    public TimeKeepingPagerAdapter(FragmentManager fm, List<List<Date>> pDates) {
        super(fm);
        mDates = pDates;
    }

    @Override
    public Fragment getItem(int position) {

        Log.e("hailpt", " TimeKeepingPagerAdapter "+position);

        TimeKeepingFragment newsFragment = new TimeKeepingFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        newsFragment.setData(mDates.get(position));
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public int getCount() {
        return mDates.size();
    }

    public void updateLayout(){

    }


}
