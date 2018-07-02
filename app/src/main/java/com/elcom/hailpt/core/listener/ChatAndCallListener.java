package com.elcom.hailpt.core.listener;

import com.elcom.hailpt.model.api.response.User;

/**
 * Created by Hailpt on 7/2/2018.
 */
public interface ChatAndCallListener {
    void doChat(User user);
    void doCall(User user);
}
