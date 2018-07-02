package com.example.arc.core.listener;

import com.example.arc.model.api.response.User;

/**
 * Created by Hailpt on 7/2/2018.
 */
public interface ChatAndCallListener {
    void doChat(User user);
    void doCall(User user);
}
