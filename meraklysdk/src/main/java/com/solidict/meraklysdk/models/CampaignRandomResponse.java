package com.solidict.meraklysdk.models;

/**
 * Created by emrahkucuk on 24/11/2017.
 */

public class CampaignRandomResponse {
    boolean succeed;
    CampaignRandomObject object;
    String message;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public CampaignRandomObject getObject() {
        return object;
    }

    public void setObject(CampaignRandomObject object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
