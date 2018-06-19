package com.example.arc.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arc.R;
import com.example.arc.core.listener.HomeFragmentCalendarListener;
import com.example.arc.model.data.TimeKeep;
import com.example.arc.util.DateTimeUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 3/5/2018.
 */

public class HomeFragmentCheckTimeView extends RelativeLayout implements View.OnClickListener {

    private TextView tvCheckIn,tvCheckOut,tvOnTime,tvLateTime,tvAbsent,tvToday;
    private ImageView imvBack;
    private HomeFragmentCalendarListener mHomeFragmentCalendarListener;
    private    ViewDataBinding binding;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;

    public HomeFragmentCheckTimeView(Context context) {
        super(context);
        init(context);
    }

    public HomeFragmentCheckTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeFragmentCheckTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public HomeFragmentCheckTimeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context) {
        View view = inflate(context, R.layout.fragment_time_keeping_second_item, this);
        tvCheckIn = (TextView)view.findViewById(R.id.tvCheckIn);
        tvCheckOut = (TextView)view.findViewById(R.id.tvCheckOut);
        tvOnTime = (TextView)view.findViewById(R.id.tvOnTime);
        tvLateTime = (TextView)view.findViewById(R.id.tvLateTime);
        tvAbsent = (TextView)view.findViewById(R.id.tvAbsent);
        tvToday = (TextView)view.findViewById(R.id.tvToday);
//
//        imvBack = view.findViewById(R.id.imvBack);
//        imvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.imvBack:
//                mHomeFragmentCalendarListener.onBack();
//                break;
        }

    }

    public void updateLayout(TimeKeep timeKeep){
        tvCheckIn.setText(timeKeep.getCheckIn());
        tvCheckOut.setText(timeKeep.getCheckOut());
        tvOnTime.setText(timeKeep.getStatistic().getOnTime().toString());
        tvLateTime.setText(timeKeep.getStatistic().getLate().toString());
        tvAbsent.setText(timeKeep.getStatistic().getAbsent().toString());
        tvToday.setText("Hôm nay, "+ DateTimeUtils.getToDayDateTime(getContext()));
    }

    public void setHomeFragmentCalendarListener(HomeFragmentCalendarListener homeFragmentCalendarListener){
        mHomeFragmentCalendarListener = homeFragmentCalendarListener;
    }

}

