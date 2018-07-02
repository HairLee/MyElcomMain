package com.elcom.hailpt.model.api.response;

/**
 * Created by Hailpt on 6/19/2018.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lunch {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status_lunch")
    @Expose
    private Integer statusLunch;
    @SerializedName("status_feedback")
    @Expose
    private Integer statusFeedback;
    @SerializedName("status_vote")
    @Expose
    private Integer statusVote;
    @SerializedName("main_dishes")
    @Expose
    private List<String> mainDishes = null;
    @SerializedName("side_dishes")
    @Expose
    private List<String> sideDishes = null;
    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("dislike")
    @Expose
    private Integer dislike;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatusLunch() {
        return statusLunch;
    }

    public void setStatusLunch(Integer statusLunch) {
        this.statusLunch = statusLunch;
    }

    public Integer getStatusFeedback() {
        return statusFeedback;
    }

    public void setStatusFeedback(Integer statusFeedback) {
        this.statusFeedback = statusFeedback;
    }

    public Integer getStatusVote() {
        return statusVote;
    }

    public void setStatusVote(Integer statusVote) {
        this.statusVote = statusVote;
    }

    public List<String> getMainDishes() {
        return mainDishes;
    }

    public void setMainDishes(List<String> mainDishes) {
        this.mainDishes = mainDishes;
    }

    public List<String> getSideDishes() {
        return sideDishes;
    }

    public void setSideDishes(List<String> sideDishes) {
        this.sideDishes = sideDishes;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

}
