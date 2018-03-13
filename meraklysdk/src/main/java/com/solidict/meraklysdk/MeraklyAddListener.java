package com.solidict.meraklysdk;

/**
 * Created by muazekici on 13.03.2018.
 */

public interface MeraklyAddListener {

    void onNoCampaignToShow();
    void onCampaignLoaded();
    void onCampaignSkipped();
    void onCloseBannerClicked();
    void onRefreshCampaign();
    void onAdLoaded();
    void onSurveyStarted();
    void onSurveyCancelled();
    void onSurveyEnded();

}
