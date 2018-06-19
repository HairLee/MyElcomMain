package com.example.arc.model.api.response;

/**
 * Created by Hailpt on 6/19/2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact implements Parcelable {

    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    protected Contact(Parcel in) {
        groupName = in.readString();
    }

    public Contact(String groupName){
        this.groupName = groupName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
