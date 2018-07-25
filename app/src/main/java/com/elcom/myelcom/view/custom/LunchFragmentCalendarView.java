package com.elcom.myelcom.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.listener.HomeFragmentCalendarListener;
import com.elcom.myelcom.model.api.response.Lunch;
import com.elcom.myelcom.model.data.TimeKeep;
import com.elcom.myelcom.util.DateTimeUtils;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 3/5/2018.
 */

public class LunchFragmentCalendarView extends RelativeLayout implements View.OnClickListener {

    private TextView tv2,tv3,tv4,tv5,tv6,tvMonth;
    private ImageView imvBack,imvCrDay2,imvCrDay3,imvCrDay4,imvCrDay5,imvCrDay6;
    private LinearLayout lnRegisLunch,lnKeepTime;
    private HomeFragmentCalendarListener mHomeFragmentCalendarListener;
    private List<TimeKeep> timeKeeps;
    List<TextView> textViewList = new ArrayList<>();

    List<ImageView> currentDayIcon = new ArrayList<>();

    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;

    public LunchFragmentCalendarView(Context context) {
        super(context);
        init(context);
    }

    public LunchFragmentCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LunchFragmentCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public LunchFragmentCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context) {
        View view = inflate(context, R.layout.fragment_regis_lunch_first_item, this);
        tv2 = (TextView)view.findViewById(R.id.tv2);
        tv3 = (TextView)view.findViewById(R.id.tv3);
        tv4 = (TextView)view.findViewById(R.id.tv4);
        tv5 = (TextView)view.findViewById(R.id.tv5);
        tv6 = (TextView)view.findViewById(R.id.tv6);

        imvCrDay2 = view.findViewById(R.id.imvCrDay2);
        imvCrDay3 = view.findViewById(R.id.imvCrDay3);
        imvCrDay4 = view.findViewById(R.id.imvCrDay4);
        imvCrDay5 = view.findViewById(R.id.imvCrDay5);
        imvCrDay6 = view.findViewById(R.id.imvCrDay6);




        tvMonth = (TextView)view.findViewById(R.id.tvMonth);
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

        currentDayIcon.add(imvCrDay2);
        currentDayIcon.add(imvCrDay3);
        currentDayIcon.add(imvCrDay4);
        currentDayIcon.add(imvCrDay5);
        currentDayIcon.add(imvCrDay6);


        imvBack = view.findViewById(R.id.imvBack);
        imvBack.setOnClickListener(this);

        setSelectedBackgroundIcon(0);
    }


    private List<Lunch> mlunchList;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public void updateData(List<Date> mDates, List<Lunch> lunchList, int currentPosDay){
        setSelectedBackgroundIcon(currentPosDay);
        mlunchList = lunchList;

        String month = dateFormat.format(mDates.get(0)).substring(3,5);
        if(month.startsWith("0")){
            tvMonth.setText("Tháng " + dateFormat.format(mDates.get(0)).substring(4,5));
        } else {
            tvMonth.setText("Tháng " + dateFormat.format(mDates.get(0)).substring(3,5));
        }

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




        if(mlunchList != null){
            for (int i = 0; i < mlunchList.size(); i++) {
                if(mlunchList.get(i).getStatusLunch() == 3){
                    textViewList.get(i).setBackgroundResource(R.drawable.tomorow_choosed_ic);
                    textViewList.get(i).setTextColor(getResources().getColor(R.color.black));
                } else if(mlunchList.get(i).getStatusLunch() == 0){
                    textViewList.get(i).setBackgroundResource(R.drawable.shape_oval_red);
                    textViewList.get(i).setTextColor(getResources().getColor(R.color.white));
                } else if(mlunchList.get(i).getStatusLunch() == 1){
                    textViewList.get(i).setBackgroundResource(R.drawable.shape_oval_green);
                    textViewList.get(i).setTextColor(getResources().getColor(R.color.white));
                }
            }
        }

        // Set icon for today ( Current day )
        for (int i = 0; i < textViewList.size(); i++) {
            if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                textViewList.get(i).setBackgroundResource(R.drawable.today_choosed_ic);
            }
        }
    }

    public void setDataForView(List<TimeKeep> timeKeeps){
        this.timeKeeps = timeKeeps;
        Gson gson = new Gson();
        String json = gson.toJson(timeKeeps.get(0));
        Log.e("hailpt"," HomeFragmentCalendarView "+json);

        // Set icon for today ( Current day )
        for (int i = 0; i < textViewList.size(); i++) {
            if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                textViewList.get(i).setBackgroundResource(R.drawable.today_choosed_ic);
            }
        }

        for (int i = 0; i < timeKeeps.size(); i++) {
            if(timeKeeps.get(i).getCheckIn().equals("")){
                textViewList.get(i).setBackgroundResource(R.drawable.shape_oval_red);
            } else {

                int timeCheckIn = Integer.parseInt(DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeeps.get(i).getCheckIn())*1000)+"").subSequence(0,1).toString());

                if(timeCheckIn < 8){
                    if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                        textViewList.get(i).setBackgroundResource(R.drawable.today_choosed_ic);
                    } else {
                        textViewList.get(i).setBackgroundResource(R.drawable.shape_oval_green);
                    }

                } else {
                    textViewList.get(i).setBackgroundResource(R.drawable.late_calendar_ic);

                }
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
                setSelectedBackgroundIcon(0);
                break;
            case R.id.tv3:
                mHomeFragmentCalendarListener.onChooseDate(1,Integer.parseInt(tv3.getText().toString()));
                setSelectedBackgroundIcon(1);
                break;
            case R.id.tv4:
                mHomeFragmentCalendarListener.onChooseDate(2,Integer.parseInt(tv4.getText().toString()));
                setSelectedBackgroundIcon(2);
                break;
            case R.id.tv5:
                mHomeFragmentCalendarListener.onChooseDate(3,Integer.parseInt(tv5.getText().toString()));
                setSelectedBackgroundIcon(3);
                break;
            case R.id.tv6:
                mHomeFragmentCalendarListener.onChooseDate(4,Integer.parseInt(tv6.getText().toString()));
                setSelectedBackgroundIcon(4);
                break;
        }

    }

    private void setSelectedBackgroundIcon(int currentPos){

        for (int i = 0; i < currentDayIcon.size(); i++) {
            if (i == currentPos){
                currentDayIcon.get(i).setVisibility(VISIBLE);
            } else  {
                currentDayIcon.get(i).setVisibility(INVISIBLE);
            }
        }

    }

    public void setHomeFragmentCalendarListener(HomeFragmentCalendarListener homeFragmentCalendarListener){
        mHomeFragmentCalendarListener = homeFragmentCalendarListener;
    }

}


