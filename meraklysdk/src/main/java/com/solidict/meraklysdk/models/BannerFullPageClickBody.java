package com.solidict.meraklysdk.models;

/**
 * Created by emrahkucuk on 07/12/2017.
 */

public class BannerFullPageClickBody {

    int campaignId;
    int surveyId;
    int bannerId;

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }
}
