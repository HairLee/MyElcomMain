package com.elcom.myelcom.view.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseFragment;
import com.elcom.myelcom.core.listener.HomeFragmentCalendarListener;
import com.elcom.myelcom.core.listener.TimeKeepingUpdateDataListener;
import com.elcom.myelcom.model.api.request.MonthReq;
import com.elcom.myelcom.model.api.request.ReasonLate;
import com.elcom.myelcom.model.api.request.TimeKeepReq;
import com.elcom.myelcom.model.data.TimeKeep;
import com.elcom.myelcom.util.DateTimeUtils;
import com.elcom.myelcom.util.ProgressDialogUtils;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.view.custom.HomeFragmentCalendarView;
import com.elcom.myelcom.view.custom.HomeFragmentCheckTimeView;
import com.elcom.myelcom.viewmodel.TimeKeepingViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeKeepingFragment extends BaseFragment<TimeKeepingViewModel> implements HomeFragmentCalendarListener,TimeKeepingUpdateDataListener {


    TimeKeepingViewModel timeKeepingViewModel;
    private List<Date> mDates = new ArrayList<>();
    private HomeFragmentCheckTimeView homeFragmentCheckTimeView;
    private HomeFragmentCalendarView homeFragmentCalendarView;
    private List<TimeKeep> timeKeeps = new ArrayList<>();

    public TimeKeepingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        return inflater.inflate(R.layout.fragment_time_keeping, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeFragmentCalendarView = view.findViewById(R.id.homeFragmentCalendarView);
        homeFragmentCheckTimeView = view.findViewById(R.id.homeFragmentCheckTimeView);

        homeFragmentCalendarView.setHomeFragmentCalendarListener(this);
        homeFragmentCheckTimeView.setHomeFragmentCalendarListener(this);

        homeFragmentCalendarView.updateData(mDates);


    }

    public void setData(List<Date> pDates){
        mDates = pDates;
    }

    @Override
    public void onBack() {
        getActivity().onBackPressed();
    }

    @Override
    public void onCancelLunchRegister() {

    }

    @Override
    public void onDoLunchRegister() {

    }

    @Override
    public void onChooseDate(int position, int dayChoosed) {
        Log.e("hailpt"," TimeKeepingFragment "+dayChoosed + " position "+position );
        homeFragmentCheckTimeView.updateLayout(position);
    }

    @Override
    public void onLikeOrDislike(boolean isLike) {
        // Dont need to use
    }

    @Override
    public void onSendFeedBack(String content, String date) {
        // Result why you are late.
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);
        ReasonLate reasonLate = new ReasonLate();
        reasonLate.setReason(content);
        reasonLate.setDate(date);
        timeKeepingViewModel.setReasonLateReq(reasonLate);
    }

    @Override
    public void onGetMonthInformation(String month) {
        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
        MonthReq monthReq = new MonthReq();
        monthReq.setMonth(month);
        monthReq.setYear("2018");
        timeKeepingViewModel.setMonthReq(monthReq);
    }

    @Override
    protected Class getViewModel() {
        return TimeKeepingViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, TimeKeepingViewModel viewModel) {
        timeKeepingViewModel = viewModel;

        String fromTime = DateTimeUtils.getDayMonthYearFromDate(mDates.get(0));
        String toTime = DateTimeUtils.getDayMonthYearFromDate(mDates.get(mDates.size() -1));
        Log.e("hailpt"," LunchRegistrationFragment fromTime "+fromTime + " toTime "+toTime);
        init();
//        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
        TimeKeepReq timeKeepReq = new TimeKeepReq();
        timeKeepReq.setFromTime(fromTime);
        timeKeepReq.setToTime(toTime);
        timeKeepingViewModel.setLoginParam(timeKeepReq);

    }

    private int mCount = 0;
    private int currentPosDay = 0;
    private void init(){
        if (mCount == 0 ){
            mCount = mCount + 1;
            timeKeepingViewModel.getTimeKeepingList().observe(this, listRestData ->{
//                        ProgressDialogUtils.dismissProgressDialog();
                        for (int i = 0; i < listRestData.data.size(); i++) {
                            if(listRestData.data.get(i).getDate().equals(DateTimeUtils.getToDayDateTimeFormat())){
                                currentPosDay = i;
                            }
                        }

                        timeKeeps = listRestData.data;
                        homeFragmentCheckTimeView.setDataForView(timeKeeps);
                        homeFragmentCheckTimeView.updateLayout(currentPosDay);

                        homeFragmentCalendarView.setDataForView(timeKeeps,currentPosDay);
                    }
            );

            timeKeepingViewModel.getMonthInformation().observe(this, statisticRestData -> {
                ProgressDialogUtils.dismissProgressDialog();
               if (statisticRestData != null && statisticRestData.status_code == 200){
                   homeFragmentCheckTimeView.updateMonthInformation(statisticRestData.data);
               }
            });

            timeKeepingViewModel.getReasonLate().observe(this, jsonElementRestData -> {
                if (jsonElementRestData != null && jsonElementRestData.status_code == 200){
                    Toaster.shortToast("Gửi thành công");
                }
            });
        }
    }

    @Override
    public void pushData(String fromDay, String toDay) {
        Log.e("hailpt"," TimeKeepingFragment fromDay"+fromDay + " toDay "+toDay);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
}
