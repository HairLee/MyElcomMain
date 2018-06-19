package com.example.arc.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.core.listener.HomeFragmentCalendarListener;
import com.example.arc.databinding.ActivityLunchRegistrationBinding;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LunchCancelReq;
import com.example.arc.model.api.request.LunchLikeReq;
import com.example.arc.model.api.request.TimeKeepReq;
import com.example.arc.model.api.response.Lunch;
import com.example.arc.util.DateTimeUtils;
import com.example.arc.util.Toaster;
import com.example.arc.view.custom.HomeFragmentCalendarView;
import com.example.arc.view.custom.LunchRegistrationContentView;
import com.example.arc.viewmodel.LunchRegistrationViewModel;
import com.google.gson.JsonElement;

import java.util.List;

public class LunchRegistrationActivity extends BaseActivity<LunchRegistrationViewModel, ActivityLunchRegistrationBinding> implements HomeFragmentCalendarListener {

    private LunchRegistrationViewModel lunchRegistrationViewModel;
    private ActivityLunchRegistrationBinding binding;
    private List<Lunch> lunchList;
    private int currentLocation = 0;
    @Override
    protected Class<LunchRegistrationViewModel> getViewModel() {
        return LunchRegistrationViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LunchRegistrationViewModel viewModel, ActivityLunchRegistrationBinding binding) {
        this.binding = binding;
        binding.homeFragmentCalendarView.setHomeFragmentCalendarListener(this);
        binding.lunchRegistrationContentView.setHomeFragmentCalendarListener(this);
        init(viewModel);

        getListCurrentDate();
    }

    private void init(LunchRegistrationViewModel viewModel){
        lunchRegistrationViewModel = viewModel;
        viewModel.cancelLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast(jsonElementRestData.message);
            hideProgressDialog();
        });

        viewModel.getLunchMenu().observe(this, listRestData -> {
            Toaster.longToast(listRestData.message);
            hideProgressDialog();
            lunchList = listRestData.data;

            int currentPosDay = 0;
            for (int i = 0; i < lunchList.size(); i++) {
                if(lunchList.get(i).getDate().equals(DateTimeUtils.getToDayDateTimeFormat(this))){
                    currentPosDay = i;
                }
            }


            binding.lunchRegistrationContentView.updateMainContent(lunchList.get(currentPosDay));
        });

        viewModel.getLikeLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast(jsonElementRestData.message);
            hideProgressDialog();
            getListCurrentDate();
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lunch_registration;
    }

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, LunchRegistrationActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void onCancelLunchRegister() {
        showProgressDialog(R.string.loading);
        LunchCancelReq lunchCancelReq = new LunchCancelReq();
        lunchCancelReq.setData(DateTimeUtils.getToDayDateTime(this));
        lunchRegistrationViewModel.setRequest(lunchCancelReq);
    }

    @Override
    public void onChooseDate(int position) {
        if (lunchList != null){
            binding.lunchRegistrationContentView.updateMainContent(lunchList.get(position));
            Log.e("hailpt"," lunchRegistrationContentView "+lunchList.get(position).getDate());
        }
        Toaster.longToast(position + "Dish");
    }

    @Override
    public void onLikeOrDislike(boolean isLike) {
        showProgressDialog(R.string.loading);
        LunchLikeReq lunchLikeReq = new LunchLikeReq();
        lunchLikeReq.setData(DateTimeUtils.getToDayDateTime(this));
        lunchLikeReq.setLike(isLike);
        lunchRegistrationViewModel.setLikeLunchRequest(lunchLikeReq);
    }

    private void getListCurrentDate(){
        showProgressDialog(R.string.loading);
        Log.e("hailpt", " getListCurrentDate Date == "+DateTimeUtils.getDayMonthYearFromDate(this, DateTimeUtils.getListCurrentDate(this).get(0)));
        binding.homeFragmentCalendarView.updateData(DateTimeUtils.getListCurrentDate(this));
        String fromTime = DateTimeUtils.getDayMonthYearFromDate(this, DateTimeUtils.getListCurrentDate(this).get(0));
        String toTime = DateTimeUtils.getDayMonthYearFromDate(this, DateTimeUtils.getListCurrentDate(this).get(DateTimeUtils.getListCurrentDate(this).size() -1));


        TimeKeepReq timeKeepReq = new TimeKeepReq();
        timeKeepReq.setFromTime(fromTime);
        timeKeepReq.setToTime(toTime);
        lunchRegistrationViewModel.setTimeRequest(timeKeepReq);
    }



}
