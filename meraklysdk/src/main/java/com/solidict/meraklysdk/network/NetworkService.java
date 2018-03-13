package com.solidict.meraklysdk.network;

import com.solidict.meraklysdk.models.BannerFullPageClickBody;
import com.solidict.meraklysdk.models.BannerInlineClickBody;
import com.solidict.meraklysdk.models.CampaignOptionClickBody;
import com.solidict.meraklysdk.models.CampaignRandomObject;
import com.solidict.meraklysdk.models.CampaignRandomResponse;
import com.solidict.meraklysdk.models.DeviceDetails;
import com.solidict.meraklysdk.models.InsertLocation;
import com.solidict.meraklysdk.models.SendSkipEventRequestBody;
import com.solidict.meraklysdk.models.SendViewEventRequestBody;
import com.solidict.meraklysdk.models.SurveyOptionClickEventBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by emrahkucuk on 01/12/2017.
 */

public interface NetworkService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("action/campaign-view")
    Call<CampaignRandomResponse> sendViewEvent(@Header("x-identifier") String identifier, @Body SendViewEventRequestBody body);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("action/campaign-skip")
    Call<CampaignRandomResponse> sendSkipEvent(@Header("x-identifier") String identifier, @Body SendSkipEventRequestBody body);


    @Headers({
            "Content-Type: application/json"
    })
    @GET("campaign/random")
    Call<CampaignRandomResponse> getCampaignRandom(@Header("x-identifier") String identifier);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("action/survey-option-click")
    Call<CampaignRandomResponse> sendSurveyOptionClickEvent(@Header("x-identifier") String identifier, @Body SurveyOptionClickEventBody body);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("action/banner-full-page-click")
    Call<CampaignRandomResponse> sendBannerFullPageClickEvent(@Header("x-identifier") String identifier, @Body BannerFullPageClickBody body);


    @Headers({
            "Content-Type: application/json"
    })
    @POST("action/campaign-option-click")
    Call<CampaignRandomResponse> sendCampaignOptionClickEvent(@Header("x-identifier") String identifier, @Body CampaignOptionClickBody body);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("action/banner-inline-click")
    Call<CampaignRandomResponse> sendBannerInlineClickEvent(@Header("x-identifier") String identifier, @Body BannerInlineClickBody body);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("update-details")
    Call<CampaignRandomResponse> sendDeviceDetailsEvent(@Header("x-identifier") String identifier, @Body DeviceDetails body);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("insert-location")
    Call<CampaignRandomResponse> sendLocationEvent(@Header("x-identifier") String identifier, @Body InsertLocation body);


}
