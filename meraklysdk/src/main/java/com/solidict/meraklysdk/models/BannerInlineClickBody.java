package com.solidict.meraklysdk.models;

/**
 * Created by emrahkucuk on 07/12/2017.
 */

public class BannerInlineClickBody {
    int campaignOptionId;
    int bannerId;

    public int getCampaignOptionId() {
        return campaignOptionId;
    }

    public void setCampaignOptionId(int campaignOptionId) {
        this.campaignOptionId = campaignOptionId;
    }

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }
}
