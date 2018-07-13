package com.elcom.hailpt.view.ui.fragment;


import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.elcom.hailpt.R;
import com.elcom.hailpt.core.AppSchedulerProvider;
import com.elcom.hailpt.core.base.BaseFragment;
import com.elcom.hailpt.core.listener.HomeFragmentCalendarListener;
import com.elcom.hailpt.core.listener.TimeKeepingUpdateDataListener;
import com.elcom.hailpt.model.api.Api;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.MonthReq;
import com.elcom.hailpt.model.api.request.ReasonLate;
import com.elcom.hailpt.model.api.request.TimeKeepReq;
import com.elcom.hailpt.model.data.Statistic;
import com.elcom.hailpt.model.data.TimeKeep;
import com.elcom.hailpt.util.ConstantsApp;
import com.elcom.hailpt.util.DateTimeUtils;
import com.elcom.hailpt.util.ProgressDialogUtils;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.view.custom.HomeFragmentCalendarView;
import com.elcom.hailpt.view.custom.HomeFragmentCheckTimeView;
import com.elcom.hailpt.view.ui.activity.TimeKeepingActivity;
import com.elcom.hailpt.viewmodel.TimeKeepingViewModel;
import com.google.gson.JsonElement;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

        TimeKeepingActivity mTimeKeepingActivity = (TimeKeepingActivity) getActivity();
        mTimeKeepingActivity.setTimeKeepingUpdateDataListener(this);

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
    public void onSendFeedBack(String content) {
        // Result why you are late.
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);
        ReasonLate reasonLate = new ReasonLate();
        reasonLate.setReason("Em bị mệt");
        reasonLate.setDate("2018-09-01");
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
        Log.e("hailpt"," TimeKeepingFragment onCreate");

        String fromTime = DateTimeUtils.getDayMonthYearFromDate(getContext(),mDates.get(0));
        String toTime = DateTimeUtils.getDayMonthYearFromDate(getContext(),mDates.get(mDates.size() -1));

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
                            if(listRestData.data.get(i).getDate().equals(DateTimeUtils.getToDayDateTimeFormat(getContext()))){
                                currentPosDay = i;
                            }
                        }

                        timeKeeps = listRestData.data;
                        homeFragmentCheckTimeView.setDataForView(timeKeeps);
                        homeFragmentCheckTimeView.updateLayout(currentPosDay);

                        homeFragmentCalendarView.setDataForView(timeKeeps);
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
