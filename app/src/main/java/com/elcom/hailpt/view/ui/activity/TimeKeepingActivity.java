package com.elcom.hailpt.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.core.listener.TimeKeepingUpdateDataListener;
import com.elcom.hailpt.databinding.ActivityTimeKeepingBinding;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.TimeKeepReq;
import com.elcom.hailpt.model.data.TimeKeep;
import com.elcom.hailpt.util.ConstantsApp;
import com.elcom.hailpt.util.DateTimeUtils;
import com.elcom.hailpt.view.adapter.CustomPagerAdapter;
import com.elcom.hailpt.view.adapter.TimeKeepingPagerAdapter;
import com.elcom.hailpt.viewmodel.LoginViewModel;
import com.elcom.hailpt.viewmodel.TimeKeepingViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

public class TimeKeepingActivity extends BaseActivity<TimeKeepingViewModel, ActivityTimeKeepingBinding> {


    private static final String KEY_ITEM_ID = "item:article";
    private FragmentManager fragmentManager;
    private int currentPosOfDay = 0;
    List<Date> mDates;
    private  List<List<Date>> mParts;
    ActivityTimeKeepingBinding binding;

    @Override
    protected Class<TimeKeepingViewModel> getViewModel() {
        return TimeKeepingViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, TimeKeepingViewModel viewModel, ActivityTimeKeepingBinding binding) {
        this.binding = binding;
        mParts = DateTimeUtils.getBigListCurrentDate(this);

        Calendar calendar = Calendar.getInstance();
        Date CurremtDate = calendar.getTime();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        for (int i = 0; i < mParts.size(); i++) {
            for (int i1 = 0; i1 < mParts.get(i).size(); i1++) {
                if(dateFormat.format(CurremtDate).equals(dateFormat.format(mParts.get(i).get(i1)))){
                    currentPosOfDay = i;
                }
            }
        }

        setupViewPager(mParts);
        binding.imvBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_time_keeping;
    }

    private void splidArray(){

        Calendar calendar = Calendar.getInstance();
        Date CurremtDate = calendar.getTime();
        mParts = chopped(mDates, 5);



    }

    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    private void setupViewPager(List<List<Date>> pDates){

        TimeKeepingPagerAdapter mDemoCollectionPagerAdapter = new TimeKeepingPagerAdapter(getSupportFragmentManager(),pDates,currentPosOfDay);

//        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this);
        ViewPager mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


        mViewPager.postDelayed(() -> mViewPager.setCurrentItem(currentPosOfDay,false), 50);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, TimeKeepingActivity.class);
        starter.putExtra(KEY_ITEM_ID, id);
        context.startActivity(starter);
    }


    public static List<Date> getDatesBetweenUsingJava7(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public void getAllDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int myMonth=cal.get(Calendar.MONTH);
        Log.e("hailpt"," getAllDayOfMonth myMonth "+myMonth);
        while (myMonth == cal.get(Calendar.MONTH)) {
            System.out.print(cal.getTime());
            Log.e("hailpt"," getAllDayOfMonth "+cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

}
