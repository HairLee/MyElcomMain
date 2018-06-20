package com.example.arc.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hailpt on 6/20/2018.
 */
public class ChangePwRq {

    @SerializedName("old_password")
    @Expose
    private String oldPassword;
    @SerializedName("new_password")
    @Expose
    private String newPassword;
    @SerializedName("confirm_password")
    @Expose
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
