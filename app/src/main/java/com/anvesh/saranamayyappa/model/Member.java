package com.anvesh.saranamayyappa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Member implements Serializable {

    private int userID;
    private String fanclubId;

    private int position;
    private final static long serialVersionUID = -7801998517321904313L;

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;

    @SerializedName("points")
    @Expose
    private int points;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userImageUrl")
    @Expose
    private String userImageUrl;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userStatus")
    @Expose
    private int userStatus;

    public Member() {
    }

    public Member(String type, String userName, int userStatus) {
        this.userName = userName;
        this.userStatus = userStatus;
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFanclubId() {
        return fanclubId;
    }

    public void setFanclubId(String fanclubId) {
        this.fanclubId = fanclubId;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

}
