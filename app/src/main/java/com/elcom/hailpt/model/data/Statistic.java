package com.elcom.hailpt.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hailpt on 6/14/2018.
 */
public class Statistic {

    @SerializedName("absent")
    @Expose
    private Integer absent;
    @SerializedName("on_time")
    @Expose
    private Integer onTime;
    @SerializedName("late")
    @Expose
    private Integer late;

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Integer getOnTime() {
        return onTime;
    }

    public void setOnTime(Integer onTime) {
        this.onTime = onTime;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

}
