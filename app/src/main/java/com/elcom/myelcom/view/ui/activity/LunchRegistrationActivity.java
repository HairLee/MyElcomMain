package com.elcom.myelcom.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseActivity;
import com.elcom.myelcom.core.listener.HomeFragmentCalendarListener;
import com.elcom.myelcom.databinding.ActivityLunchRegistrationBinding;
import com.elcom.myelcom.model.api.request.LunchCancelReq;
import com.elcom.myelcom.model.api.request.LunchFeedBackReq;
import com.elcom.myelcom.model.api.request.LunchLikeReq;
import com.elcom.myelcom.model.api.request.TimeKeepReq;
import com.elcom.myelcom.model.api.response.Lunch;
import com.elcom.myelcom.util.DateTimeUtils;
import com.elcom.myelcom.util.KeyBoardUtils;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.view.dialog.LunchRegisDialog;
import com.elcom.myelcom.viewmodel.LunchRegistrationViewModel;

import java.util.List;

public class LunchRegistrationActivity extends BaseActivity<LunchRegistrationViewModel, ActivityLunchRegistrationBinding> implements HomeFragmentCalendarListener {

    private LunchRegistrationViewModel lunchRegistrationViewModel;
    private ActivityLunchRegistrationBinding binding;
    private List<Lunch> lunchList;
    private int currentLocation = 0;
    private  LunchRegistrationViewModel viewModel;
    @Override
    protected Class<LunchRegistrationViewModel> getViewModel() {
        return LunchRegistrationViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LunchRegistrationViewModel viewModel, ActivityLunchRegistrationBinding binding) {
        this.binding = binding;
        this.viewModel = viewModel;
        binding.homeFragmentCalendarView.setHomeFragmentCalendarListener(this);
        binding.lunchRegistrationContentView.setHomeFragmentCalendarListener(this);
        init(viewModel);

        getListCurrentDate();
        Log.e("hailpt"," currentDay ==== "+ DateTimeUtils.currentDay());
    }

    private void init(LunchRegistrationViewModel viewModel){
        binding.homeFragmentCalendarView.hideLnKeepTime();
        lunchRegistrationViewModel = viewModel;
        viewModel.registerLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast(jsonElementRestData.message);
            hideProgressDialog();
        });


        viewModel.cancelLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast("Hủy cơm thành công");
            hideProgressDialog();
        });

        viewModel.getLunchMenu().observe(this, listRestData -> {
            hideProgressDialog();
            lunchList = listRestData.data;

            int currentPosDay = 0;
            for (int i = 0; i < lunchList.size(); i++) {
                if(lunchList.get(i).getDate().equals(DateTimeUtils.getToDayDateTimeFormat())){
                    currentPosDay = i;
                }
            }
            binding.lunchRegistrationContentView.updateMainContent(lunchList.get(currentPosDay),DateTimeUtils.currentDay());
        });

        viewModel.getLikeLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast(jsonElementRestData.message);
            hideProgressDialog();
            getListCurrentDate();
        });

        viewModel.sendFeedBackLunch().observe(this, data -> {
            hideProgressDialog();
            if (data != null){
                Log.e("hailpt"," sendFeedBackLunch OK");
            }
        });

        binding.imvBack.setOnClickListener(v -> onBackPressed());
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

        LunchRegisDialog lunchRegisDialog = new LunchRegisDialog(this, true, () -> {
            showProgressDialog();
            LunchCancelReq lunchCancelReq = new LunchCancelReq();
            lunchCancelReq.setData(DateTimeUtils.getToDayDateTime(getApplicationContext()));
            lunchRegistrationViewModel.setRequest(lunchCancelReq);

        });
        lunchRegisDialog.show();
    }

    @Override
    public void onDoLunchRegister() {
        LunchRegisDialog lunchRegisDialog = new LunchRegisDialog(this, false, () -> {
            showProgressDialog();
            LunchCancelReq lunchCancelReq = new LunchCancelReq();
            lunchCancelReq.setData(DateTimeUtils.getToDayDateTime(getApplicationContext()));
            lunchRegistrationViewModel.setRegisterLunchRequest(lunchCancelReq);
        });
        lunchRegisDialog.show();
    }

    @Override
    public void onChooseDate(int position, int dayChoosed) {
        if (lunchList != null){
            binding.lunchRegistrationContentView.updateMainContent(lunchList.get(position),dayChoosed);
            Log.e("hailpt"," lunchRegistrationContentView "+lunchList.get(position).getDate());
        }
//        Toaster.longToast(position + "Dish");
    }

    @Override
    public void onLikeOrDislike(boolean isLike) {
        showProgressDialog();
        LunchLikeReq lunchLikeReq = new LunchLikeReq();
        lunchLikeReq.setData(DateTimeUtils.getToDayDateTime(this));
        lunchLikeReq.setLike(isLike);
        lunchRegistrationViewModel.setLikeLunchRequest(lunchLikeReq);
    }

    @Override
    public void onSendFeedBack(String content, String date) {
        showProgressDialog();
        KeyBoardUtils.hideKeyboard(this);
        LunchFeedBackReq lunchFeedBackReq = new LunchFeedBackReq();
        lunchFeedBackReq.setDate(DateTimeUtils.getToDayDateTime(this));
        lunchFeedBackReq.setFeedback_content(content);
        viewModel.setFeedBackunchRequest(lunchFeedBackReq);
    }

    @Override
    public void onGetMonthInformation(String month) {

    }

    private void getListCurrentDate(){
        showProgressDialog();
//        Log.e("hailpt", " getListCurrentDate Date == "+DateTimeUtils.getDayMonthYearFromDate(this, DateTimeUtils.getListCurrentDate(this).get(0)));
        binding.homeFragmentCalendarView.updateData(DateTimeUtils.getListCurrentDate(this));
        String fromTime = DateTimeUtils.getDayMonthYearFromDate(DateTimeUtils.getListCurrentDate(this).get(0));
        String toTime = DateTimeUtils.getDayMonthYearFromDate( DateTimeUtils.getListCurrentDate(this).get(DateTimeUtils.getListCurrentDate(this).size() -1));

        TimeKeepReq timeKeepReq = new TimeKeepReq();
        timeKeepReq.setFromTime(fromTime);
        timeKeepReq.setToTime(toTime);
        lunchRegistrationViewModel.setTimeRequest(timeKeepReq);
    }



}
