package com.solidict.meraklysdk.models;

import java.io.Serializable;

/**
 * Created by muazekici on 13.03.2018.
 */

public class InsertLocation implements Serializable {

    private float latitude;
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
