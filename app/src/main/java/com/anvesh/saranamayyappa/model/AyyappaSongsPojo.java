package com.anvesh.saranamayyappa.model;

import java.io.Serializable;

/**
 * Created by snallamothu on 2/15/2018.
 */

public class AyyappaSongsPojo implements Serializable {
    String _id;
    String videoUrl;
    String songUrl;
    String imageUrl;

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
