package com.elcom.myelcom.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.listener.HomeFragmentCalendarListener;
import com.elcom.myelcom.model.data.Statistic;
import com.elcom.myelcom.model.data.TimeKeep;
import com.elcom.myelcom.util.DateTimeUtils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private String currentDate = "";
    private TextView month1,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12;

    private List<TextView> monthList;
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

        month1 = view.findViewById(R.id.month1);
        month2 = view.findViewById(R.id.month2);
        month3 = view.findViewById(R.id.month3);
        month4 = view.findViewById(R.id.month4);
        month5 = view.findViewById(R.id.month5);
        month6 = view.findViewById(R.id.month6);
        month7 = view.findViewById(R.id.month7);
        month8 = view.findViewById(R.id.month8);
        month9 = view.findViewById(R.id.month9);
        month10 = view.findViewById(R.id.month10);
        month11 = view.findViewById(R.id.month11);
        month12 = view.findViewById(R.id.month12);

        monthList = new ArrayList<>();
        monthList.add(month1);
        monthList.add(month2);
        monthList.add(month3);
        monthList.add(month4);
        monthList.add(month5);
        monthList.add(month6);
        monthList.add(month7);
        monthList.add(month8);
        monthList.add(month9);
        monthList.add(month10);
        monthList.add(month11);
        monthList.add(month12);

        month1.setOnClickListener(this);
        month2.setOnClickListener(this);
        month3.setOnClickListener(this);
        month4.setOnClickListener(this);
        month5.setOnClickListener(this);
        month6.setOnClickListener(this);
        month7.setOnClickListener(this);
        month8.setOnClickListener(this);
        month9.setOnClickListener(this);
        month10.setOnClickListener(this);
        month11.setOnClickListener(this);
        month12.setOnClickListener(this);

        imvSendFeedBack.setOnClickListener(v -> {
            mHomeFragmentCalendarListener.onSendFeedBack(edtLate.getText().toString(),currentDate);
        });

        tvDate.setText("Hôm nay, "+ DateTimeUtils.getToDayDateTime(getContext()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.month1:
                mHomeFragmentCalendarListener.onGetMonthInformation("1");
                changeColorOfMonth("T1");
                break;
            case R.id.month2:
                mHomeFragmentCalendarListener.onGetMonthInformation("2");
                changeColorOfMonth("T2");
                break;
            case R.id.month3:
                mHomeFragmentCalendarListener.onGetMonthInformation("3");
                changeColorOfMonth("T3");
                break;
            case R.id.month4:
                mHomeFragmentCalendarListener.onGetMonthInformation("4");
                changeColorOfMonth("T4");
                break;
            case R.id.month5:
                mHomeFragmentCalendarListener.onGetMonthInformation("5");
                changeColorOfMonth("T5");
                break;
            case R.id.month6:
                mHomeFragmentCalendarListener.onGetMonthInformation("6");
                changeColorOfMonth("T6");
                break;
            case R.id.month7:
                mHomeFragmentCalendarListener.onGetMonthInformation("7");
                changeColorOfMonth("T7");
                break;
            case R.id.month8:
                mHomeFragmentCalendarListener.onGetMonthInformation("8");
                changeColorOfMonth("T8");
                break;
            case R.id.month9:
                mHomeFragmentCalendarListener.onGetMonthInformation("9");
                changeColorOfMonth("T9");
                break;
            case R.id.month10:
                mHomeFragmentCalendarListener.onGetMonthInformation("10");
                changeColorOfMonth("T10");
                break;
            case R.id.month11:
                mHomeFragmentCalendarListener.onGetMonthInformation("11");
                changeColorOfMonth("T11");
                break;
            case R.id.month12:
                mHomeFragmentCalendarListener.onGetMonthInformation("12");
                changeColorOfMonth("T12");
                break;
        }

    }

    private void changeColorOfMonth(String month){

        for (int i = 0; i < monthList.size(); i++) {
           if (monthList.get(i).getText().equals(month)){
               monthList.get(i).setTextColor(getResources().getColor(R.color.color_blue_qb));
//               monthList.get(i).setTypeface(null, Typeface.BOLD);
           } else {
               monthList.get(i).setTextColor(getResources().getColor(R.color.color_monthText));
           }
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
            currentDate = timeKeeps.get(pos).getDate();
        }
    }

    public void updateMonthInformation(Statistic statistic){
        tvOnTime.setText(statistic.getOnTime().toString());
        tvLateTime.setText(statistic.getLate().toString());
        tvAbsent.setText(statistic.getAbsent().toString());
    }

    public void updateLayout(TimeKeep timeKeep){
        Gson gson = new Gson();
        String json = gson.toJson(timeKeep);
        Log.e("hailpt"," TimeKeepingRepository HomeFragmentCheckTimeView "+json);
        rlLate.setVisibility(GONE);
        if(!timeKeep.getCheckIn().equals("")){
            tvCheckIn.setText(DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeep.getCheckIn())*1000)+""));

            int timeCheckIn = Integer.parseInt(tvCheckIn.getText().subSequence(1,2).toString());
            String time = tvCheckIn.getText().toString();

            if((time.startsWith("0") && timeCheckIn < 8 && !time.contains("PM")) || time.equals("08:00:00 AM")){
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
            tvCheckIn.setText("--:--");
        }

        if(!timeKeep.getCheckOut().equals("")){
            tvCheckOut.setText(DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeep.getCheckOut())*1000)+""));
        } else {
            tvCheckOut.setText("--:--");
        }

        if(timeKeep.getStatistic() != null){
            tvOnTime.setText(timeKeep.getStatistic().getOnTime().toString());
            tvLateTime.setText(timeKeep.getStatistic().getLate().toString());
            tvAbsent.setText(timeKeep.getStatistic().getAbsent().toString());
        }

        //If tomorrow
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date strDate = null;
            try {
                strDate = sdf.parse(timeKeep.getDate());
                if (System.currentTimeMillis() < strDate.getTime()) {
                    tvToday.setText("Ngày mai");
                    tvToday.setTextColor(getResources().getColor(R.color.onTime));
                }

            } catch (ParseException e) {
                e.printStackTrace();
        }
    }

    public void setHomeFragmentCalendarListener(HomeFragmentCalendarListener homeFragmentCalendarListener){
        mHomeFragmentCalendarListener = homeFragmentCalendarListener;
    }

}


