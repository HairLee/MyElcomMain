package com.elcom.hailpt.core.listener;

/**
 * Created by Hailpt on 6/13/2018.
 */
public interface HomeFragmentCalendarListener {
    void onBack();
    void onCancelLunchRegister();

    void onDoLunchRegister();

    void onChooseDate(int position, int dayChoosed);

    void onLikeOrDislike(boolean isLike);

    void onSendFeedBack(String content);


}
