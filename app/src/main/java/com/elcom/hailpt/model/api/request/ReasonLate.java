package com.elcom.hailpt.model.api.request;

/**
 * Created by Hailpt on 7/13/2018.
 */
public class ReasonLate {

    private String date;
    private String reason;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
