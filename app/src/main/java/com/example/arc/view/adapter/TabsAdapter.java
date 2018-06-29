package com.example.arc.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.arc.view.ui.fragment.BlankFragment;

/**
 * Created by Hailpt on 6/28/2018.
 */
public class TabsAdapter extends FragmentPagerAdapter {


    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("hailpt", " TimeKeepingPagerAdapter "+position + " mCureentPos "+position);
        return new BlankFragment();
    }

    @Override
    public int getCount() {
        return 10;
    }
}
