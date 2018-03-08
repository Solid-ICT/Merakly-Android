package com.solidict.meraklysdk.models;

import java.io.Serializable;

/**
 * Created by emrahkucuk on 27/11/2017.
 */

public class BannerModel  implements Serializable{
    int id;
    String imageUrl;
    String targetUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
