package com.elcom.hailpt.core.listener;

import com.elcom.hailpt.model.data.TimeKeep;

import java.util.List;

/**
 * Created by Hailpt on 6/14/2018.
 */
public interface TimeKeepingUpdateDataListener {
    void pushData(String fromDay, String toDay);
}
