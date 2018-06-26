package com.example.arc.core.listener;

import android.view.View;

import com.example.arc.model.api.response.User;

/**
 * Created by Hailpt on 6/20/2018.
 */
public interface AllContactFragmentListener {

    void onViewProfile(View view,int userId);
    void onCallVideo(User user);
}
