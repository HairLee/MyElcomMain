package com.example.arc.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arc.R;
import com.example.arc.core.listener.HomeFragmentCalendarListener;
import com.example.arc.model.api.response.Lunch;
import com.example.arc.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 3/5/2018.
 */

public class LunchRegistrationContentView extends RelativeLayout implements View.OnClickListener {

    private LinearLayout lnRegisLunch,lnLike,lnUnlike;
    private HomeFragmentCalendarListener mHomeFragmentCalendarListener;
    private TextView tvMainDish1,tvMainDish2,tvMainDish3,tvMainDish4,tvMainDish5,tvLunchRegister;
    private TextView tvSideDish1,tvSideDish2,tvSideDish3,tvSideDish4,tvSideDish5;
    private TextView tvLike, tvDislike,txtRegisterLunch,tvNumberOfMessage;
    private List<TextView> mMainDishLish = new ArrayList<>();
    private List<TextView> mSideDishLish = new ArrayList<>();
    private ImageView imvLunch,imvLikeIc,imvSendFeedBack;
    private EditText edtFeedback;
    private boolean isLunchRegister = false;
    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;

    public LunchRegistrationContentView(Context context) {
        super(context);
        init(context);
    }

    public LunchRegistrationContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LunchRegistrationContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public LunchRegistrationContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context) {
        View view = inflate(context, R.layout.lunch_registration_content_view_item, this);
        lnRegisLunch = view.findViewById(R.id.lnRegisLunch);
        lnRegisLunch.setOnClickListener(this);

        lnLike = view.findViewById(R.id.lnLike);
        lnLike.setOnClickListener(this);

        lnUnlike = view.findViewById(R.id.lnUnlike);
        lnUnlike.setOnClickListener(this);

        tvMainDish1 = view.findViewById(R.id.tvMainDish1);
        tvMainDish2 = view.findViewById(R.id.tvMainDish2);
        tvMainDish3 = view.findViewById(R.id.tvMainDish3);
        tvMainDish4 = view.findViewById(R.id.tvMainDish4);
        tvMainDish5 = view.findViewById(R.id.tvMainDish5);

        tvSideDish1 = view.findViewById(R.id.tvSideDish1);
        tvSideDish2 = view.findViewById(R.id.tvSideDish2);
        tvSideDish3 = view.findViewById(R.id.tvSideDish3);
        tvSideDish4 = view.findViewById(R.id.tvSideDish4);
        tvSideDish5 = view.findViewById(R.id.tvSideDish5);

        tvNumberOfMessage = view.findViewById(R.id.tvNumberOfMessage);


        edtFeedback = view.findViewById(R.id.edtFeedback);
        imvSendFeedBack = view.findViewById(R.id.imvSendFeedBack);


        tvLunchRegister = view.findViewById(R.id.tvLunchRegister);
        imvLunch = view.findViewById(R.id.imvLunch);
        imvLikeIc = view.findViewById(R.id.imvLikeIc);

        tvLike = view.findViewById(R.id.tvLike);
        tvDislike = view.findViewById(R.id.tvDislike);
        txtRegisterLunch = view.findViewById(R.id.txtRegisterLunch);

        mMainDishLish.add(tvMainDish1);
        mMainDishLish.add(tvMainDish2);
        mMainDishLish.add(tvMainDish3);
        mMainDishLish.add(tvMainDish4);
        mMainDishLish.add(tvMainDish5);

        mSideDishLish.add(tvSideDish1);
        mSideDishLish.add(tvSideDish2);
        mSideDishLish.add(tvSideDish3);
        mSideDishLish.add(tvSideDish4);
        mSideDishLish.add(tvSideDish5);

        edtFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() <= 200){
                    tvNumberOfMessage.setText(s.length()+"/200");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imvSendFeedBack.setOnClickListener(v -> {
            mHomeFragmentCalendarListener.onSendFeedBack(edtFeedback.getText().toString());
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvBack:
                mHomeFragmentCalendarListener.onBack();
                break;
            case R.id.lnRegisLunch:
                if(isLunchRegister){
                    mHomeFragmentCalendarListener.onCancelLunchRegister();
                } else {
                    mHomeFragmentCalendarListener.onDoLunchRegister();
                }

                break;
            case R.id.lnLike:
                mHomeFragmentCalendarListener.onLikeOrDislike(true);
                break;
            case R.id.lnUnlike:
                mHomeFragmentCalendarListener.onLikeOrDislike(false);
                break;
        }

    }

    public void updateMainContent(Lunch lunch, int dayChoosed){
        resetView();

        Log.e("hailpt"," DayChoosed "+dayChoosed);


        /* MainDish Start */
        int totalMainDish = 5 - lunch.getMainDishes().size(); // 3
        int count = 0;
        for (int i = mMainDishLish.size() - 1; i >= totalMainDish; i--) {
            if (count < totalMainDish){
                mMainDishLish.remove(i);
                count = count +1;
            }
        }


        for (int i = 0; i < mMainDishLish.size(); i++) {
            if (lunch.getMainDishes().size() > i){
                mMainDishLish.get(i).setVisibility(VISIBLE);
                mMainDishLish.get(i).setText((i+1)+". "+lunch.getMainDishes().get(i));
            }
        }

        /*MainDish End*/

        /* SideDish Start */
        int totalSideDish = 5 - lunch.getMainDishes().size(); // 3
        int sideCount = 0;
        for (int i = mSideDishLish.size() - 1; i >= totalSideDish; i--) {
            if (sideCount < totalSideDish){
                mSideDishLish.remove(i);
                sideCount = sideCount +1;
            }
        }

        for (int i = 0; i < mSideDishLish.size(); i++) {
            if (lunch.getSideDishes().size() > i){
                mSideDishLish.get(i).setVisibility(VISIBLE);
                mSideDishLish.get(i).setText((i+1)+". "+lunch.getSideDishes().get(i));
            }
        }
        /*SideDish End*/

        tvLike.setText(lunch.getLike().toString());
        tvDislike.setText(lunch.getDislike().toString());
        int statusLunch = lunch.getStatusLunch();

        Log.e("hailpt"," statusLunch ~~~> "+statusLunch);

        // Dont wanna have a lunch - dont use card to go to the company - have a day off
//        if(statusLunch == 0){
//            setTextForLunchRegister("Không ăn");
//            imvLunch.setBackgroundResource(R.drawable.lunch_not_eat);
//            return;
//        }

        // Previous Day, the title is always ..
        if(DateTimeUtils.isCurrentTimeIsBefore9Am() && DateTimeUtils.currentDay() == dayChoosed){
            if(statusLunch == 4){
                // have not order
                isLunchRegister = false;
                lnRegisLunch.setVisibility(VISIBLE);
                imvLunch.setVisibility(INVISIBLE);
                lnRegisLunch.setBackgroundResource(R.drawable.radius_blue_bg_blue_srtoke_layout);
                txtRegisterLunch.setText("ĐĂNG KÝ");
            } else {
                isLunchRegister = true;
                lnRegisLunch.setVisibility(VISIBLE);
                lnRegisLunch.setBackgroundResource(R.drawable.radius_red_bg_red_srtoke_layout);
                txtRegisterLunch.setText("HỦY ĐĂNG KÝ");
            }
        } else {
            imvLunch.setVisibility(VISIBLE);
            lnRegisLunch.setVisibility(INVISIBLE);
            switch (statusLunch){

                case 0:
                    setTextForLunchRegister("Không ăn");
                    imvLunch.setBackgroundResource(R.drawable.lunch_not_eat);
                    break;
                case 1:
                    setTextForLunchRegister("Đã ăn");
                    imvLunch.setBackgroundResource(R.drawable.lunch_eat_ic);
                    break;
                case 2:
                    setTextForLunchRegister("Đăng ký không ăn");
                    break;
                case 3:
                    setTextForLunchRegister("Ngày mai");
                    break;
                case 4:
                    setTextForLunchRegister("Hủy ăn trưa");
                    break;
            }

        }

    }

    private void setTextForLunchRegister(String content){
        if(!content.equals("")){
            imvLunch.setVisibility(VISIBLE);
            lnRegisLunch.setVisibility(INVISIBLE);
            tvLunchRegister.setVisibility(VISIBLE);
            tvLunchRegister.setText(content);
        } else  {
            imvLunch.setVisibility(INVISIBLE);
            lnRegisLunch.setVisibility(VISIBLE);
            tvLunchRegister.setVisibility(INVISIBLE);
        }

    }

    public void setHomeFragmentCalendarListener(HomeFragmentCalendarListener homeFragmentCalendarListener){
        mHomeFragmentCalendarListener = homeFragmentCalendarListener;
    }

    private void resetView(){
        for (int i = 0; i < mMainDishLish.size(); i++) {
            mMainDishLish.get(i).setVisibility(GONE);
        }
    }

}


