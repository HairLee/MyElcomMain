package com.example.arc.view.ui.fragment;


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
import android.widget.TextView;


import com.example.arc.R;
import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.core.base.BaseFragment;
import com.example.arc.core.listener.HomeFragmentCalendarListener;
import com.example.arc.core.listener.TimeKeepingUpdateDataListener;
import com.example.arc.model.api.Api;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.TimeKeepReq;
import com.example.arc.model.data.TimeKeep;
import com.example.arc.util.ConstantsApp;
import com.example.arc.util.DateTimeUtils;
import com.example.arc.util.Toaster;
import com.example.arc.view.custom.HomeFragmentCalendarView;
import com.example.arc.view.custom.HomeFragmentCheckTimeView;
import com.example.arc.view.ui.activity.TimeKeepingActivity;
import com.example.arc.viewmodel.TimeKeepingViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        HomeFragmentCalendarView homeFragmentCalendarView = view.findViewById(R.id.homeFragmentCalendarView);
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

    }

    @Override
    protected Class getViewModel() {
        return TimeKeepingViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, TimeKeepingViewModel viewModel) {
        timeKeepingViewModel = viewModel;
        Log.e("hailpt"," pushData onCreate");

    }

    private int mCount = 0;
    private int currentPosDay = 0;
    private void init(){
        if (mCount == 0 ){
            mCount = mCount + 1;
            timeKeepingViewModel.getTimeKeepingList().observe(this, listRestData ->{

                        homeFragmentCheckTimeView.updateLayout(listRestData.data.get(0));

//                        Toaster.longToast("Data = "+listRestData.data.get(0).getDate());


//                        for (int i = 0; i < listRestData.data.size(); i++) {
//                            if(listRestData.data.get(i).getDate().equals(DateTimeUtils.getToDayDateTimeFormat(getContext()))){
//                                currentPosDay = i;
//                            }
//                        }
//
//                        if(currentPosDay != 0){
//                            homeFragmentCheckTimeView.updateLayout(listRestData.data.get(currentPosDay));
//                        } else {
//                            homeFragmentCheckTimeView.updateLayout(listRestData.data.get(0));
//                        }

                        Log.e("hailpt"," TimeKeepingppFragment init "+currentPosDay);

                        timeKeeps = listRestData.data;
                        homeFragmentCheckTimeView.setDataForView(timeKeeps);
                    }
            );
        }
    }

    @Override
    public void pushData(String fromDay, String toDay) {
        Log.e("hailpt"," pushData fromDay"+fromDay + " toDay "+toDay);
//        showProgressDialog(R.string.text_push_notification_message);
        init();
        TimeKeepReq timeKeepReq = new TimeKeepReq();
        timeKeepReq.setFromTime(fromDay);
        timeKeepReq.setToTime(toDay);
        timeKeepingViewModel.setLoginParam(timeKeepReq);


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("hailpt", "setUserVisibleHint: " + isVisibleToUser);
    }
}
