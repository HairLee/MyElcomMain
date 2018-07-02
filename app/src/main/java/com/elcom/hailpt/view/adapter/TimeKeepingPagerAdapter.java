package com.elcom.hailpt.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.elcom.hailpt.view.ui.fragment.BlankFragment;
import com.elcom.hailpt.view.ui.fragment.IncomeCallFragment;
import com.elcom.hailpt.view.ui.fragment.TimeKeepingFragment;

import java.util.Date;
import java.util.List;

/**
 * Created by Hailpt on 6/5/2018.
 */
public class TimeKeepingPagerAdapter extends FragmentStatePagerAdapter {

    private List<List<Date>> mDates;
    private int mCureentPos = 0;
    private int mPrevious= 0;
    public TimeKeepingPagerAdapter(FragmentManager fm, List<List<Date>> pDates, int currentPosOfDay) {
        super(fm);
        mDates = pDates;
        this.mCureentPos = currentPosOfDay;
    }

    @Override
    public Fragment getItem(int position) {

        Log.e("hailpt", " TimeKeepingPagerAdapter "+position + " mCureentPos "+mCureentPos);

//        if ( position == 25){
            TimeKeepingFragment newsFragment = new TimeKeepingFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            newsFragment.setData(mDates.get(position));
            newsFragment.setArguments(args);
            return newsFragment;
//        } else {
//            return new BlankFragment();
//        }

    }

    @Override
    public int getCount() {
        return mDates.size();
    }

    public void updateLayout(){

    }


}
