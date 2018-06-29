package com.example.arc.model.api.response;

/**
 * Created by Hailpt on 6/8/2018.
 */
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User  implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("job_title")
    @Expose
    private String job_title;

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
    private Object hotline;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("quickblox_id")
    @Expose
    private Integer quickbloxId;
    @SerializedName("quickblox_user")
    @Expose
    private Object quickbloxUser;
    @SerializedName("quickblox_pwd")
    @Expose
    private Object quickbloxPwd;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
    @SerializedName("updated_at")
    @Expose
    private Integer updatedAt;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("group_name")
    @Expose
    private Object groupName;

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }


    public User(){

    }

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        staffId = in.readString();
        cardId = in.readString();
        email = in.readString();
        mobile = in.readString();
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        if (in.readByte() == 0) {
            isActive = null;
        } else {
            isActive = in.readInt();
        }
        apiToken = in.readString();
        if (in.readByte() == 0) {
            createdAt = null;
        } else {
            createdAt = in.readInt();
        }
        if (in.readByte() == 0) {
            updatedAt = null;
        } else {
            updatedAt = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(staffId);
        dest.writeString(cardId);
        dest.writeString(email);
        dest.writeString(mobile);
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        if (isActive == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isActive);
        }
        dest.writeString(apiToken);
        if (createdAt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdAt);
        }
        if (updatedAt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(updatedAt);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public Object getHotline() {
        return hotline;
    }

    public void setHotline(Object hotline) {
        this.hotline = hotline;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getQuickbloxId() {
        return quickbloxId;
    }

    public void setQuickbloxId(Integer quickbloxId) {
        this.quickbloxId = quickbloxId;
    }

    public Object getQuickbloxUser() {
        return quickbloxUser;
    }

    public void setQuickbloxUser(Object quickbloxUser) {
        this.quickbloxUser = quickbloxUser;
    }

    public Object getQuickbloxPwd() {
        return quickbloxPwd;
    }

    public void setQuickbloxPwd(Object quickbloxPwd) {
        this.quickbloxPwd = quickbloxPwd;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    @BindingAdapter({"app:glideLoading", "app:placeholde"})
    public static void bindImage(ImageView imageView, String url, Drawable placeHolder) {
        Glide.with(imageView)
                .setDefaultRequestOptions(RequestOptions.placeholderOf(placeHolder))
                .asBitmap()
                .load(url)
                .into(imageView);
    }

}
