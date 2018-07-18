package com.anvesh.saranamayyappa.model;

import java.io.Serializable;

public class GroupMembersPojo implements Serializable {

    String fullName;
    String status;
    String mobileNumber;
    String type;
    String profileImage;
    String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    /*
    public GroupMembersPojo(String fullName, String status, String mobileNumber, String type, String profileImage) {
        this.fullName = fullName;
        this.status = status;
        this.mobileNumber = mobileNumber;
        this.type = type;
        this.profileImage = profileImage;
    }*/

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


}
