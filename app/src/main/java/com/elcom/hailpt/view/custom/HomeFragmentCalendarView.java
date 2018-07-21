package com.elcom.hailpt.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.listener.HomeFragmentCalendarListener;
import com.elcom.hailpt.model.data.TimeKeep;
import com.elcom.hailpt.util.DateTimeUtils;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 3/5/2018.
 */

public class HomeFragmentCalendarView extends RelativeLayout implements View.OnClickListener {

    private TextView tv2,tv3,tv4,tv5,tv6,tvMonth;
    private ImageView imvBack;
    private LinearLayout lnRegisLunch,lnKeepTime;
    private HomeFragmentCalendarListener mHomeFragmentCalendarListener;
    private List<TimeKeep> timeKeeps;
    List<TextView> textViewList = new ArrayList<>();
    List<ImageView> currentDayIcon = new ArrayList<>();

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

        ImageView imvCrDay2 = view.findViewById(R.id.imvCrDay2);
        ImageView imvCrDay3 = view.findViewById(R.id.imvCrDay3);
        ImageView imvCrDay4 = view.findViewById(R.id.imvCrDay4);
        ImageView imvCrDay5 = view.findViewById(R.id.imvCrDay5);
        ImageView imvCrDay6 = view.findViewById(R.id.imvCrDay6);


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
    }

    public void hideLnKeepTime(){
        lnKeepTime.setVisibility(GONE);
    }

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public void updateData(List<Date> mDates){
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
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


        // Set icon for today ( Current day )
        for (int i = 0; i < textViewList.size(); i++) {
            if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                textViewList.get(i).setBackgroundResource(R.drawable.today_choosed_ic);
            }
        }


    }

    public void setDataForView(List<TimeKeep> pTimeKeeps,int currentPosDay){
        setSelectedBackgroundIcon(currentPosDay);
        this.timeKeeps = pTimeKeeps;
        Gson gson = new Gson();
        String json = gson.toJson(timeKeeps.get(0));
        Log.e("hailpt"," HomeFragmentCalendarView "+json);

        if(pTimeKeeps != null){
            for (int i = 0; i < timeKeeps.size(); i++) {
                if(timeKeeps.get(i).getCheckIn().equals("")){
                    if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                        textViewList.get(i).setBackgroundResource(R.drawable.today_choose_late);
                    } else {
                        textViewList.get(i).setBackgroundResource(R.drawable.shape_oval_red);
                    }
                } else {

                    int timeCheckIn = Integer.parseInt(DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeeps.get(i).getCheckIn())*1000)+"").subSequence(1,2).toString());
                    String time = textViewList.get(i).getText().toString();

                    if((timeCheckIn < 8 && !DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeeps.get(i).getCheckIn())*1000)+"").contains("PM") ) || DateTimeUtils.convertLongToTimeDate((Long.parseLong(timeKeeps.get(i).getCheckIn())*1000)+"").contains("08:00 AM") ){
                        if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                            textViewList.get(i).setBackgroundResource(R.drawable.today_choosed_ic);
                        } else {
                            textViewList.get(i).setBackgroundResource(R.drawable.shape_oval_green);
                        }

                    } else {

                        if (textViewList.get(i).getText().toString().startsWith(DateTimeUtils.getDayMonthYear())){
                            textViewList.get(i).setBackgroundResource(R.drawable.late_calendar_ic);
                        } else {
                            textViewList.get(i).setBackgroundResource(R.drawable.shape_oval_oringe);
                        }
                    }
                }
            }


            //If tomorrow
            for (int i = 0; i < timeKeeps.size(); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date strDate = null;
                try {
                    strDate = sdf.parse(timeKeeps.get(i).getDate());
                    if (System.currentTimeMillis() < strDate.getTime()) {
                        textViewList.get(i).setBackgroundResource(R.drawable.tomorow_choosed_ic);
                        textViewList.get(i).setTextColor(getResources().getColor(R.color.black));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
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


