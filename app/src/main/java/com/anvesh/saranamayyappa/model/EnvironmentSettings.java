package com.anvesh.saranamayyappa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sbogi on 3/10/2017.
 */

public class EnvironmentSettings implements Serializable {

    private static final long serialVersionUID = -8021514954409533730L;

    @SerializedName("base_url")
    private String base_url;
    @SerializedName("events_url")
    private String events_url;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("end_point")
    private String end_point;
    @SerializedName("notification_id")
    private String notification_id;
    @SerializedName("notification_hub_name")
    private String notification_hub_name;
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("client_secret")
    private String client_secret;
    private String publisher_key;
    private String subscriber_key;


    public String getPublisher_key() {
        return publisher_key;
    }

    public void setPublisher_key(String publisher_key) {
        this.publisher_key = publisher_key;
    }

    public String getSubscriber_key() {
        return subscriber_key;
    }

    public void setSubscriber_key(String subscriber_key) {
        this.subscriber_key = subscriber_key;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEnd_point() {
        return end_point;
    }

    public void setEnd_point(String end_point) {
        this.end_point = end_point;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getNotification_hub_name() {
        return notification_hub_name;
    }

    public void setNotification_hub_name(String notification_hub_name) {
        this.notification_hub_name = notification_hub_name;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
