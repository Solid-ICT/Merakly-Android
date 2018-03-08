package com.solidict.meraklysdk.models;

/**
 * Created by emrahkucuk on 07/12/2017.
 */

public class SurveyOptionClickEventBody {
    int campaignId;
    int campaignOptionId;
    int surveyOptionId;

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getCampaignOptionId() {
        return campaignOptionId;
    }

    public void setCampaignOptionId(int campaignOptionId) {
        this.campaignOptionId = campaignOptionId;
    }

    public int getSurveyOptionId() {
        return surveyOptionId;
    }

    public void setSurveyOptionId(int surveyOptionId) {
        this.surveyOptionId = surveyOptionId;
    }
}
