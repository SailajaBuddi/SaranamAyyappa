package com.anvesh.saranamayyappa.model;

import java.io.Serializable;

public class NearByGuruPojo implements Serializable {

    private String statusMessage;
    private String groupId;
    private String groupName;
    private String groupImageUrl;
    private String locationName;
    private String createdDateTime;
    private String createdUserId;
    private String createdUserName;
    private String createdUserProfileImage;
    private int joinStatus;
    private int memberCount;
    private Boolean owner;

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImageUrl() {
        return groupImageUrl;
    }

    public void setGroupImageUrl(String groupImageUrl) {
        this.groupImageUrl = groupImageUrl;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public String getCreatedUserProfileImage() {
        return createdUserProfileImage;
    }

    public void setCreatedUserProfileImage(String createdUserProfileImage) {
        this.createdUserProfileImage = createdUserProfileImage;
    }

    public int getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(int joinStatus) {
        this.joinStatus = joinStatus;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}
