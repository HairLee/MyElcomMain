package com.example.arc.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arc.R;
import com.example.arc.core.listener.HomeFragmentCalendarListener;
import com.example.arc.model.api.response.Lunch;
import com.example.arc.util.DateTimeUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 3/5/2018.
 */

public class HomeFragmentCalendarView extends RelativeLayout implements View.OnClickListener {

    private TextView tv2,tv3,tv4,tv5,tv6;
    private ImageView imvBack;
    private LinearLayout lnRegisLunch,lnKeepTime;
    private HomeFragmentCalendarListener mHomeFragmentCalendarListener;
    List<TextView> textViewList = new ArrayList<>();


    public String firstDayOfWeek(){
     return  tv2.getText().toString();
    }

    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;

    public HomeFragmentCalendarView(Context context) {
        super(context);
        init(context);
    }

    public HomeFragmentCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeFragmentCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public HomeFragmentCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context) {
        View view = inflate(context, R.layout.fragment_time_keeping_first_item, this);
        tv2 = (TextView)view.findViewById(R.id.tv2);
        tv3 = (TextView)view.findViewById(R.id.tv3);
        tv4 = (TextView)view.findViewById(R.id.tv4);
        tv5 = (TextView)view.findViewById(R.id.tv5);
        tv6 = (TextView)view.findViewById(R.id.tv6);
        lnKeepTime = (LinearLayout) view.findViewById(R.id.lnKeepTime);

        textViewList.add(tv2);
        textViewList.add(tv3);
        textViewList.add(tv4);
        textViewList.add(tv5);
        textViewList.add(tv6);

        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);

        imvBack = view.findViewById(R.id.imvBack);
        imvBack.setOnClickListener(this);
    }

    public void hideLnKeepTime(){
        lnKeepTime.setVisibility(GONE);
    }

    public void updateData(List<Date> mDates){
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        if(mDates.size() > 0){
            tv2.setText(dateFormat.format(mDates.get(0)).substring(0,2));
        }

        if(mDates.size() > 1){
            tv3.setText(dateFormat.format(mDates.get(1)).substring(0,2));
        }

        if(mDates.size() > 2){
            tv4.setText(dateFormat.format(mDates.get(2)).substring(0,2));
        }

        if(mDates.size() > 3){
            tv5.setText(dateFormat.format(mDates.get(3)).substring(0,2));
        }

        if(mDates.size() > 4){
            tv6.setText(dateFormat.format(mDates.get(4)).substring(0,2));
        }

        for (int i = 0; i < textViewList.size(); i++) {
            if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                textViewList.get(i).setTextColor(getResources().getColor(R.color.color_accent));
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvBack:
                mHomeFragmentCalendarListener.onBack();
                break;
            case R.id.lnRegisLunch:
                mHomeFragmentCalendarListener.onCancelLunchRegister();
                break;
            case R.id.tv2:
                mHomeFragmentCalendarListener.onChooseDate(0,Integer.parseInt(tv2.getText().toString()));
                break;
            case R.id.tv3:
                mHomeFragmentCalendarListener.onChooseDate(1,Integer.parseInt(tv3.getText().toString()));
                break;
            case R.id.tv4:
                mHomeFragmentCalendarListener.onChooseDate(2,Integer.parseInt(tv4.getText().toString()));
                break;
            case R.id.tv5:
                mHomeFragmentCalendarListener.onChooseDate(3,Integer.parseInt(tv5.getText().toString()));
                break;
            case R.id.tv6:
                mHomeFragmentCalendarListener.onChooseDate(4,Integer.parseInt(tv6.getText().toString()));
                break;
        }

    }

    public void setHomeFragmentCalendarListener(HomeFragmentCalendarListener homeFragmentCalendarListener){
        mHomeFragmentCalendarListener = homeFragmentCalendarListener;
    }

}


