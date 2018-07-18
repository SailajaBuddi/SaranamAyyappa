package com.anvesh.saranamayyappa.model;

import java.io.Serializable;

public class NearByPujaPojo implements Serializable {

    String eventId;
    String eventTitle;
    String eventImageUrl;
    String eventStartDateTime;
    String eventEndDateTime;
    String rsvpClosingDateTime;
    String eventLocation;
    String createdDateTime;
    String createdUserId;
    String createdUserName;
    String createdUserProfileImage;
    int joinStatus;
    int memberCount;
    int yesCount;
    int maybeCount;
    int noCount;
    int noResponseCount;


    String eventDescription;
    String modifiedDateTime;


    public int getYesCount() {
        return yesCount;
    }

    public void setYesCount(int yesCount) {
        this.yesCount = yesCount;
    }

    public int getMaybeCount() {
        return maybeCount;
    }

    public void setMaybeCount(int maybeCount) {
        this.maybeCount = maybeCount;
    }

    public int getNoCount() {
        return noCount;
    }

    public void setNoCount(int noCount) {
        this.noCount = noCount;
    }

    public int getNoResponseCount() {
        return noResponseCount;
    }

    public void setNoResponseCount(int noResponseCount) {
        this.noResponseCount = noResponseCount;
    }

    public String getRsvpClosingDateTime() {
        return rsvpClosingDateTime;
    }

    public void setRsvpClosingDateTime(String rsvpClosingDateTime) {
        this.rsvpClosingDateTime = rsvpClosingDateTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public String getEventStartDateTime() {
        return eventStartDateTime;
    }

    public void setEventStartDateTime(String eventStartDateTime) {
        this.eventStartDateTime = eventStartDateTime;
    }

    public String getEventEndDateTime() {
        return eventEndDateTime;
    }

    public void setEventEndDateTime(String eventEndDateTime) {
        this.eventEndDateTime = eventEndDateTime;
    }


    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
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

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }


}
