package com.elcom.hailpt.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseFragment;
import com.elcom.hailpt.core.listener.HomeFragmentCalendarListener;
import com.elcom.hailpt.model.api.request.LunchCancelReq;
import com.elcom.hailpt.model.api.request.LunchFeedBackReq;
import com.elcom.hailpt.model.api.request.LunchLikeReq;
import com.elcom.hailpt.model.api.request.TimeKeepReq;
import com.elcom.hailpt.model.api.response.Lunch;
import com.elcom.hailpt.util.DateTimeUtils;
import com.elcom.hailpt.util.KeyBoardUtils;
import com.elcom.hailpt.util.ProgressDialogUtils;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.view.custom.HomeFragmentCalendarView;
import com.elcom.hailpt.view.custom.HomeFragmentCheckTimeView;
import com.elcom.hailpt.view.custom.LunchFragmentCalendarView;
import com.elcom.hailpt.view.custom.LunchRegistrationContentView;
import com.elcom.hailpt.view.dialog.LunchRegisDialog;
import com.elcom.hailpt.viewmodel.LunchRegistrationViewModel;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LunchRegistrationFragment extends BaseFragment<LunchRegistrationViewModel> implements HomeFragmentCalendarListener {


    private List<Date> mDates;
    private LunchRegistrationContentView lunchRegistrationContentView;
    private LunchFragmentCalendarView lunchFragmentCalendarView;
    LunchRegistrationViewModel lunchRegistrationViewModel;
    private List<Lunch> lunchList;
    private String currentDayChoosed = "";
    private int currentDay = 0;
    public LunchRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lunch_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lunchFragmentCalendarView = view.findViewById(R.id.lunchFragmentCalendarView);
        lunchRegistrationContentView = view.findViewById(R.id.lunchRegistrationContentView);

        lunchFragmentCalendarView.setHomeFragmentCalendarListener(this);
        lunchRegistrationContentView.setHomeFragmentCalendarListener(this);



    }

    @Override
    protected Class<LunchRegistrationViewModel> getViewModel() {
        return LunchRegistrationViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LunchRegistrationViewModel viewModel) {
        lunchRegistrationViewModel = viewModel;
        currentDayChoosed = DateTimeUtils.getToDayDateTime(getContext());
        init(lunchRegistrationViewModel);
        getListCurrentDate();
    }

    private void init(LunchRegistrationViewModel viewModel){
        viewModel.registerLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast(jsonElementRestData.message);
            ProgressDialogUtils.dismissProgressDialog();
        });


        viewModel.cancelLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast("Hủy cơm thành công");
            ProgressDialogUtils.dismissProgressDialog();
        });

        viewModel.getLunchMenu().observe(this, listRestData -> {
            ProgressDialogUtils.dismissProgressDialog();
            lunchList = listRestData.data;

            int currentPosDay = 0;
            for (int i = 0; i < lunchList.size(); i++) {
                if(lunchList.get(i).getDate().equals(DateTimeUtils.getToDayDateTimeFormat())){
                    currentPosDay = i;
                }
            }
            lunchRegistrationContentView.updateMainContent(lunchList.get(currentPosDay),currentDay);

            lunchFragmentCalendarView.updateData(mDates, lunchList);
        });

        viewModel.getLikeLunch().observe(this, jsonElementRestData -> {
            Toaster.longToast(jsonElementRestData.message);
            ProgressDialogUtils.dismissProgressDialog();
            getListCurrentDate();
        });

        viewModel.sendFeedBackLunch().observe(this, data -> {
            ProgressDialogUtils.dismissProgressDialog();
            if (data != null){
                Log.e("hailpt"," sendFeedBackLunch OK");
            }
        });

    }

    public void setData(List<Date> pDates){
        mDates = pDates;
    }


    @Override
    public void onBack() {

    }

    @Override
    public void onCancelLunchRegister() {
        LunchRegisDialog lunchRegisDialog = new LunchRegisDialog(getContext(), true, () -> {
            ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
            LunchCancelReq lunchCancelReq = new LunchCancelReq();
            lunchCancelReq.setData(DateTimeUtils.getToDayDateTime(getContext()));
            lunchRegistrationViewModel.setRequest(lunchCancelReq);

        });
        lunchRegisDialog.show();
    }

    @Override
    public void onDoLunchRegister() {
        LunchRegisDialog lunchRegisDialog = new LunchRegisDialog(getContext(), false, () -> {
            ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
            LunchCancelReq lunchCancelReq = new LunchCancelReq();
            lunchCancelReq.setData(DateTimeUtils.getToDayDateTime(getContext()));
            lunchRegistrationViewModel.setRegisterLunchRequest(lunchCancelReq);
        });
        lunchRegisDialog.show();
    }

    @Override
    public void onChooseDate(int position, int dayChoosed) {
        if (lunchList != null){
            currentDay = dayChoosed;
            currentDayChoosed = lunchList.get(position).getDate();
            lunchRegistrationContentView.updateMainContent(lunchList.get(position),dayChoosed);
        }
    }

    @Override
    public void onLikeOrDislike(boolean isLike) {
        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
        LunchLikeReq lunchLikeReq = new LunchLikeReq();
        lunchLikeReq.setData(currentDayChoosed);
        lunchLikeReq.setLike(isLike);
        lunchRegistrationViewModel.setLikeLunchRequest(lunchLikeReq);
    }

    @Override
    public void onSendFeedBack(String content) {
        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
        KeyBoardUtils.hideKeyboard(getActivity());
        LunchFeedBackReq lunchFeedBackReq = new LunchFeedBackReq();
        lunchFeedBackReq.setDate(DateTimeUtils.getToDayDateTime(getContext()));
        lunchFeedBackReq.setFeedback_content(content);
        lunchRegistrationViewModel.setFeedBackunchRequest(lunchFeedBackReq);
    }

    @Override
    public void onGetMonthInformation(String month) {

    }


    private void getListCurrentDate(){
        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
//        Log.e("hailpt", " getListCurrentDate Date == "+DateTimeUtils.getDayMonthYearFromDate(this, DateTimeUtils.getListCurrentDate(this).get(0)));
//        lunchFragmentCalendarView.updateData(DateTimeUtils.getListCurrentDate(getContext()));
//        String fromTime = DateTimeUtils.getDayMonthYearFromDate(getContext(), DateTimeUtils.getListCurrentDate(getContext()).get(0));
//        String toTime = DateTimeUtils.getDayMonthYearFromDate(getContext(), DateTimeUtils.getListCurrentDate(getContext()).get(DateTimeUtils.getListCurrentDate(getContext()).size() -1));

        String fromTime = DateTimeUtils.getDayMonthYearFromDate(getContext(),mDates.get(0));
        String toTime = DateTimeUtils.getDayMonthYearFromDate(getContext(),mDates.get(mDates.size() -1));

        Log.e("hailpt"," getListCurrentDate fromTime "+fromTime + " toTime "+toTime);

        TimeKeepReq timeKeepReq = new TimeKeepReq();
        timeKeepReq.setFromTime(fromTime);
        timeKeepReq.setToTime(toTime);
        lunchRegistrationViewModel.setTimeRequest(timeKeepReq);
    }
}
