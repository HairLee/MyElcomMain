package com.elcom.hailpt.model.api.request;

/**
 * Created by Hailpt on 6/19/2018.
 */
public class LunchLikeReq {
    private String date;
    private boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getData() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
    }
}
