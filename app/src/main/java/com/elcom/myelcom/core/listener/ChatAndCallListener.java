package com.elcom.myelcom.core.listener;

import com.elcom.myelcom.model.api.response.User;

/**
 * Created by Hailpt on 7/2/2018.
 */
public interface ChatAndCallListener {
    void doChat(User user);
    void doCall(User user);
}
