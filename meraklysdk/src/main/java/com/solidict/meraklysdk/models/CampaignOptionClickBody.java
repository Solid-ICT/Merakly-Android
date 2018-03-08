package com.solidict.meraklysdk.models;

/**
 * Created by emrahkucuk on 07/12/2017.
 */

public class CampaignOptionClickBody {
    int campaignOptionId;
    float replyTime;

    public int getCampaignOptionId() {
        return campaignOptionId;
    }

    public void setCampaignOptionId(int campaignOptionId) {
        this.campaignOptionId = campaignOptionId;
    }

    public float getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(float replyTime) {
        this.replyTime = replyTime;
    }
}
