package com.elcom.hailpt.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.listener.HomeFragmentCalendarListener;
import com.elcom.hailpt.model.data.TimeKeep;
import com.elcom.hailpt.util.DateTimeUtils;
import com.google.gson.Gson;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 3/5/2018.
 */

public class HomeFragmentCheckTimeView extends RelativeLayout implements View.OnClickListener {

    private TextView tvCheckIn,tvCheckOut,tvOnTime,tvLateTime,tvAbsent,tvToday,tvDate;
    private ImageView imvBack;
    private HomeFragmentCalendarListener mHomeFragmentCalendarListener;
    private List<TimeKeep> timeKeeps;
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
        tvDate = (TextView)view.findViewById(R.id.tvDate);
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

    public void setDataForView(List<TimeKeep> timeKeeps){
        this.timeKeeps = timeKeeps;
        updateLayout(timeKeeps.get(0));
    }

    public void updatelayout(List<TimeKeep> timeKeeps, int currentLocation){
        this.timeKeeps = timeKeeps;
        tvCheckIn.setText(timeKeeps.get(currentLocation).getCheckIn());
        tvCheckOut.setText(timeKeeps.get(currentLocation).getCheckOut());
        tvOnTime.setText(timeKeeps.get(currentLocation).getStatistic().getOnTime().toString());
        tvLateTime.setText(timeKeeps.get(currentLocation).getStatistic().getLate().toString());
        tvAbsent.setText(timeKeeps.get(currentLocation).getStatistic().getAbsent().toString());
        tvDate.setText("Hôm nay, "+ DateTimeUtils.getToDayDateTime(getContext()));

    }

    public void updateLayout(int pos){
        if (timeKeeps != null && timeKeeps.size() > 0){
            updateLayout(timeKeeps.get(pos));
        }
    }

    public void updateLayout(TimeKeep timeKeep){
        Gson gson = new Gson();
        String json = gson.toJson(timeKeep);
        Log.e("hailpt"," TimeKeepingRepository HomeFragmentCheckTimeView "+json);

        tvCheckIn.setText(timeKeep.getCheckIn());
        tvCheckOut.setText(timeKeep.getCheckOut());
        tvOnTime.setText(timeKeep.getStatistic().getOnTime().toString());
        tvLateTime.setText(timeKeep.getStatistic().getLate().toString());
        tvAbsent.setText(timeKeep.getStatistic().getAbsent().toString());
        tvToday.setText(timeKeep.getDate());
        tvDate.setText("Hôm nay, "+ DateTimeUtils.getToDayDateTime(getContext()));
    }

    public void setHomeFragmentCalendarListener(HomeFragmentCalendarListener homeFragmentCalendarListener){
        mHomeFragmentCalendarListener = homeFragmentCalendarListener;
    }

}


