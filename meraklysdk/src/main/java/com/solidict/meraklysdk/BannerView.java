package com.solidict.meraklysdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solidict.meraklysdk.models.BannerFullPageClickBody;
import com.solidict.meraklysdk.models.BannerInlineClickBody;
import com.solidict.meraklysdk.models.CampaignOptionClickBody;
import com.solidict.meraklysdk.models.CampaignRandomResponse;
import com.solidict.meraklysdk.models.SendSkipEventRequestBody;
import com.solidict.meraklysdk.models.SendViewEventRequestBody;
import com.solidict.meraklysdk.models.SurveyModel;
import com.squareup.picasso.Picasso;

import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emrahkucuk on 24/11/2017.
 */

public class BannerView extends RelativeLayout {
    BannerListener bannerListener;
    String encodedIdentifier;

    private TextView tv_question;
    private TextView tv_thanks;
    ImageView iv_banner;
    ImageView iv_close_banner;
    View rl_banner;
    private ImageView iv_close;
    private int campaignId = -1;
    private SegmentedGroup segmentedGroup;
    private View rl_loading;

    long successfullLoadTime = -1;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBannerListener(BannerListener bannerListener) {
        this.bannerListener = bannerListener;
    }

    public void init(String apiKey, String secretKey) {
        IdentifierModel identifierModel = new IdentifierModel();
        identifierModel.setApiKey(apiKey);
        identifierModel.setSecretKey(secretKey);

        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();
//        identifierModel.setDeviceId(android.provider.Settings.System.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        identifierModel.setDeviceId(mac);
        identifierModel.setVersion(BuildConfig.VERSION_CODE);
        identifierModel.setLocale("tr");//TODO make this automatic later
        identifierModel.setOsType(OsType.IOS.getValue());//TODO change this later
        identifierModel.setOsVersion(Build.VERSION.RELEASE);

        Gson gson = new Gson();
        String json = gson.toJson(identifierModel);

        encodedIdentifier = Base64.encodeToString(json.getBytes(), Base64.NO_WRAP);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner_layout, this);
        tv_question = view.findViewById(R.id.tv_question);
        tv_thanks = view.findViewById(R.id.tv_thanks);
        iv_close = view.findViewById(R.id.iv_close);
        rl_loading = view.findViewById(R.id.rl_loading);
        segmentedGroup = view.findViewById(R.id.segmented2);

        iv_banner = view.findViewById(R.id.iv_banner);
        rl_banner = view.findViewById(R.id.rl_banner);
        iv_close_banner = view.findViewById(R.id.iv_close_banner);


        iv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SendSkipEventRequestBody sendSkipEventRequestBody = new SendSkipEventRequestBody();
                sendSkipEventRequestBody.setCampaignId(campaignId);
                Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendSkipEvent(encodedIdentifier, sendSkipEventRequestBody);
                call.enqueue(new Callback<CampaignRandomResponse>() {
                    @Override
                    public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

                    }
                });
                load();
            }
        });
    }

    public void load() {

        rl_loading.setVisibility(VISIBLE);
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().getCampaignRandom(encodedIdentifier);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {
                CampaignRandomResponse campaignRandomResponse = response.body();


                if (campaignRandomResponse != null && campaignRandomResponse.isSucceed()) {
                    sendViewEventRequest();
                    bind(campaignRandomResponse);
                    successfullLoadTime = System.currentTimeMillis();
                } else if(!campaignRandomResponse.isSucceed() && campaignRandomResponse.getObject() == null){
                    tv_thanks.setText(campaignRandomResponse.getMessage());
                    tv_thanks.setVisibility(VISIBLE);
                    bannerListener.onLoadError(campaignRandomResponse.getMessage());
                } else {
                    tv_thanks.setVisibility(INVISIBLE);
                }
                rl_loading.setVisibility(GONE);

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {
                rl_loading.setVisibility(GONE);
            }
        });
    }


    private void sendViewEventRequest() {
        SendViewEventRequestBody sendViewEventRequestBody = new SendViewEventRequestBody();
        sendViewEventRequestBody.setCampaignId(campaignId);
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendViewEvent(encodedIdentifier, sendViewEventRequestBody);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }

    private void bind(final CampaignRandomResponse campaignRandomResponse) {
        segmentedGroup.setVisibility(VISIBLE);
        tv_thanks.setVisibility(INVISIBLE);
        campaignId = campaignRandomResponse.getObject().getId();

        tv_question.setText(campaignRandomResponse.getObject().getQuestion());


        segmentedGroup.removeAllViews();
        for (int i = 0; i < campaignRandomResponse.getObject().getOptions().size(); i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.radio_button_layout, null);
            radioButton.setText(campaignRandomResponse.getObject().getOptions().get(i).getOption());
            final int finalI = i;
            radioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (successfullLoadTime != -1) {
                        CampaignOptionClickBody body = new CampaignOptionClickBody();
                        body.setCampaignOptionId(campaignRandomResponse.getObject().getOptions().get(finalI).getId());
                        float replyTime = ((System.currentTimeMillis() - successfullLoadTime) * 1f) / 1000f;
                        body.setReplyTime(replyTime);
                        successfullLoadTime = -1;

                        //sendCampaignOptionClickEvent(body); TODO comment out this later
                    }


                    if (campaignRandomResponse.getObject().getOptions().get(finalI).getSurvey() != null) {
                        openPopup(campaignRandomResponse.getObject().getOptions().get(finalI).getSurvey(), campaignRandomResponse.getObject().getOptions().get(finalI).getId());
                    } else if (campaignRandomResponse.getObject().getOptions().get(finalI).getBanner() != null){
                        Picasso.with(getContext()).load(campaignRandomResponse.getObject().getOptions().get(finalI).getBanner().getImageUrl()).into(iv_banner);
                        rl_banner.setVisibility(View.VISIBLE);
                        if (campaignRandomResponse.getObject().getOptions().get(finalI).getBanner().getTargetUrl() != null) {
                            iv_banner.setClickable(true);
                            iv_banner.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    BannerInlineClickBody body = new BannerInlineClickBody();
                                    body.setBannerId(campaignRandomResponse.getObject().getOptions().get(finalI).getBanner().getId());
                                    body.setCampaignOptionId(campaignRandomResponse.getObject().getOptions().get(finalI).getId());

                                    Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendBannerInlineClickEvent(encodedIdentifier, body);
                                    call.enqueue(new Callback<CampaignRandomResponse>() {
                                        @Override
                                        public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

                                        }
                                    });

                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(campaignRandomResponse.getObject().getOptions().get(finalI).getBanner().getTargetUrl()));
                                    getContext().startActivity(i);
                                }
                            });
                        } else {
                            iv_banner.setClickable(false);
                        }
                        iv_close_banner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rl_banner.setVisibility(GONE);
                                load();
                            }
                        });
                    } else {
                        segmentedGroup.setVisibility(INVISIBLE);
                        tv_thanks.setText(getContext().getString(R.string.thanks));
                        tv_thanks.setVisibility(VISIBLE);
                    }
                }
            });
            segmentedGroup.addView(radioButton);
        }
        segmentedGroup.setTintColor(Color.WHITE, Color.BLACK);

        segmentedGroup.updateBackground();


    }

    private void sendCampaignOptionClickEvent(CampaignOptionClickBody body) {
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendCampaignOptionClickEvent(encodedIdentifier, body);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }

    private void openPopup(SurveyModel surveyModel, int campaignOptionId) {
        Intent intent = new Intent(getContext(), PopupActivity.class);

        intent.putExtra(getContext().getString(R.string.intent_key_survey_model), surveyModel);
        intent.putExtra(getContext().getString(R.string.intent_key_encoded_identifier), encodedIdentifier);
        intent.putExtra(getContext().getString(R.string.intent_key_campaign_id), campaignId);
        intent.putExtra(getContext().getString(R.string.intent_key_campaign_option_id), campaignOptionId);

        getContext().startActivity(intent);
    }

    public void onResume() {
        load();
    }
}
