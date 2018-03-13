package com.solidict.meraklysdk.models;

/**
 * Created by emrahkucuk on 30/11/2017.
 */

public class IdentifierModel {

    private String deviceId;
    private int version;
    private int osType;
    private String osVersion;
    private String locale;
    private String apiKey;
    private String secretKey;
    private double latitude;
    private double longitude;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public int getVersion() {
        return version;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }
    public int getOsType() {
        return osType;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
    public String getOsVersion() {
        return osVersion;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getLocale() {
        return locale;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public String getApiKey() {
        return apiKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getSecretKey() {
        return secretKey;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}