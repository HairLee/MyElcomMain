package com.example.arc.core.listener;

/**
 * Created by Hailpt on 6/13/2018.
 */
public interface HomeFragmentCalendarListener {
    void onBack();
    void onCancelLunchRegister();

    void onChooseDate(int position);

    void onLikeOrDislike(boolean isLike);


}
