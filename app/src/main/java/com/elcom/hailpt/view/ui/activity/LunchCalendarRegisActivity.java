package com.elcom.hailpt.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityRegisCalandarLunchBinding;
import com.elcom.hailpt.util.DateTimeUtils;
import com.elcom.hailpt.util.NetworkConnectionChecker;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.view.adapter.RegisLunchPagerAdapter;
import com.elcom.hailpt.viewmodel.LunchRegistrationViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LunchCalendarRegisActivity extends BaseActivity<LunchRegistrationViewModel, ActivityRegisCalandarLunchBinding> implements  NetworkConnectionChecker.OnConnectivityChangedListener {


    private static final String KEY_ITEM_ID = "item:article";
    private FragmentManager fragmentManager;
    private int currentPosOfDay = 0;
    List<Date> mDates;
    private  List<List<Date>> mParts;
    ActivityRegisCalandarLunchBinding binding;
    private NetworkConnectionChecker networkConnectionChecker;


    @Override
    protected Class<LunchRegistrationViewModel> getViewModel() {
        return LunchRegistrationViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LunchRegistrationViewModel viewModel, ActivityRegisCalandarLunchBinding binding) {
        this.binding = binding;
        mParts = DateTimeUtils.getBigListCurrentDate(this);
        initWiFiManagerListener();
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


        binding.imvBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_regis_calandar_lunch;
    }

    private void setupViewPager(List<List<Date>> pDates){
        RegisLunchPagerAdapter mDemoCollectionPagerAdapter = new RegisLunchPagerAdapter(getSupportFragmentManager(),pDates,currentPosOfDay);
        ViewPager mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.postDelayed(() -> mViewPager.setCurrentItem(currentPosOfDay,false), 50);
    }

    private void initWiFiManagerListener() {
        networkConnectionChecker = new NetworkConnectionChecker(getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkConnectionChecker.registerListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkConnectionChecker.unregisterListener(this);
    }

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, LunchCalendarRegisActivity.class);
        starter.putExtra(KEY_ITEM_ID, id);
        context.startActivity(starter);
    }


    @Override
    public void connectivityChanged(boolean availableNow) {
       if(availableNow){
           setupViewPager(mParts);
       } else {
           Toaster.shortToast(R.string.check_internet_plz);
       }
    }


}
