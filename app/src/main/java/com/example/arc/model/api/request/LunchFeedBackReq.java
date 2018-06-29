package com.example.arc.model.api.request;

/**
 * Created by Hailpt on 6/29/2018.
 */
public class LunchFeedBackReq {
    private String date;
    private String feedback_content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }
}
