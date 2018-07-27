package com.elcom.myelcom.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hailpt on 6/14/2018.
 */
public class TimeKeep {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("check-in")
    @Expose
    private String checkIn;
    @SerializedName("check-out")
    @Expose
    private String checkOut;

    @SerializedName("statistic")
    @Expose
    private Statistic statistic;

    @SerializedName("status_checkin")
    @Expose
    private int status_checkin;

    public int getStatus_checkin() {
        return status_checkin;
    }

    public void setStatus_checkin(int status_checkin) {
        this.status_checkin = status_checkin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

}
