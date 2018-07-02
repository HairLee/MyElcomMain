package com.elcom.hailpt.model.api.request;

/**
 * Created by Hailpt on 6/14/2018.
 */
public class TimeKeepReq {

    private String fromTime;
    private String toTime;

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
