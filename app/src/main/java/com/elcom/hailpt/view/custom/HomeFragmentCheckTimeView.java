package com.elcom.hailpt.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

import okhttp3.internal.Util;


/**
 * Created by admin on 3/5/2018.
 */

public class HomeFragmentCheckTimeView extends RelativeLayout implements View.OnClickListener {

    private TextView tvCheckIn,tvCheckOut,tvOnTime,tvLateTime,tvAbsent,tvToday,tvDate;
    private RelativeLayout rlLate;
    private ImageView imvBack,imvSendFeedBack;
    private HomeFragmentCalendarListener mHomeFragmentCalendarListener;
    private List<TimeKeep> timeKeeps;
    private EditText edtLate;
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
        rlLate = view.findViewById(R.id.rlLate);
        imvSendFeedBack = view.findViewById(R.id.imvSendFeedBack);
        edtLate = view.findViewById(R.id.edtLate);

        imvSendFeedBack.setOnClickListener(v -> {
            mHomeFragmentCalendarListener.onSendFeedBack(edtLate.getText().toString());
        });
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
        rlLate.setVisibility(GONE);
        if(!timeKeep.getCheckIn().equals("")){
            tvCheckIn.setText(DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeep.getCheckIn())*1000)+""));


            int timeCheckIn = Integer.parseInt(tvCheckIn.getText().subSequence(0,1).toString());

            if(timeCheckIn < 8){
                tvToday.setText("Đúng giờ");
                tvToday.setTextColor(getResources().getColor(R.color.onTime));
            } else {
                tvToday.setText("Đi muộn");
                rlLate.setVisibility(VISIBLE);
                tvToday.setTextColor(getResources().getColor(R.color.late));
            }



        } else {
            tvToday.setText("Vắng mặt");
            tvToday.setTextColor(getResources().getColor(R.color.lost));
            tvCheckIn.setText("Không có dữ liệu");
        }

        if(!timeKeep.getCheckOut().equals("")){
            tvCheckOut.setText(DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeep.getCheckOut())*1000)+""));
        } else {
            tvCheckOut.setText("Không có dữ liệu");
        }

        tvOnTime.setText(timeKeep.getStatistic().getOnTime().toString());
        tvLateTime.setText(timeKeep.getStatistic().getLate().toString());
        tvAbsent.setText(timeKeep.getStatistic().getAbsent().toString());



//        tvDate.setText("Hôm nay, "+ DateTimeUtils.getToDayDateTime(getContext()));

        tvDate.setText(timeKeep.getDate());
    }

    public void setHomeFragmentCalendarListener(HomeFragmentCalendarListener homeFragmentCalendarListener){
        mHomeFragmentCalendarListener = homeFragmentCalendarListener;
    }

}


