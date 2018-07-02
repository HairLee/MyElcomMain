package com.elcom.hailpt.model.api.response;

/**
 * Created by Hailpt on 6/18/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactSuggest {

    @SerializedName("call_chat_at")
    @Expose
    private String callChatAt;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("staff_id")
    @Expose
    private String staffId;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("hotline")
    @Expose
    private String hotline;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("created_at")
    @Expose
    private int createdAt;
    @SerializedName("quickblox_id")
    @Expose
    private String quickbloxId;
    @SerializedName("status_mark")
    @Expose
    private Integer statusMark;

    public String getCallChatAt() {
        return callChatAt;
    }

    public void setCallChatAt(String callChatAt) {
        this.callChatAt = callChatAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public String getQuickbloxId() {
        return quickbloxId;
    }

    public void setQuickbloxId(String quickbloxId) {
        this.quickbloxId = quickbloxId;
    }

    public Integer getStatusMark() {
        return statusMark;
    }

    public void setStatusMark(Integer statusMark) {
        this.statusMark = statusMark;
    }

}