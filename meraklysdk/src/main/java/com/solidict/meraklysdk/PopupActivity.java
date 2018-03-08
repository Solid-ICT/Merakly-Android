package com.solidict.meraklysdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.solidict.meraklysdk.models.BannerFullPageClickBody;
import com.solidict.meraklysdk.models.CampaignRandomResponse;
import com.solidict.meraklysdk.models.SurveyModel;
import com.solidict.meraklysdk.models.SurveyOptionClickEventBody;
import com.squareup.picasso.Picasso;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupActivity extends AppCompatActivity implements SurveyFragmentCallbackListener {
    SurveyModel surveyModel;
    ImageView iv_banner;
    ImageView iv_close_banner;
    View rl_banner;
    ViewPager viewPager;
    String encodedIdentifier;
    SurveyViewPagerAdapter pagerAdapter;
    private int campaignOptionId;
    private int campaignId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setFinishOnTouchOutside(false);
        surveyModel = (SurveyModel) getIntent().getExtras().getSerializable(getString(R.string.intent_key_survey_model));
        encodedIdentifier = getIntent().getExtras().getString(getString(R.string.intent_key_encoded_identifier));
        campaignId = getIntent().getExtras().getInt(getString(R.string.intent_key_campaign_id));
        campaignOptionId = getIntent().getExtras().getInt(getString(R.string.intent_key_campaign_option_id));

        ImageView iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        iv_close_banner = (ImageView) findViewById(R.id.iv_close_banner);
        rl_banner = findViewById(R.id.rl_banner);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        pagerAdapter = new SurveyViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.init(surveyModel, this, this);
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
        pagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (surveyModel.getBanner() != null) {
            Picasso.with(this).load(surveyModel.getBanner().getImageUrl()).into(iv_banner);
        }
    }


    @Override
    public void onSurveyCompleted(int surveyOptionId) {
        //sendSurveyOptionClickEvent(surveyOptionId); TODO comment out this later

        if (hasMoreQuestions()) {
            goToNextQuestion();
        } else {
            showBannerIfExists();
        }
    }

    private void sendSurveyOptionClickEvent(int surveyOptionId) {
        SurveyOptionClickEventBody body = new SurveyOptionClickEventBody();
        body.setCampaignId(campaignId);
        body.setCampaignOptionId(campaignOptionId);
        body.setSurveyOptionId(surveyOptionId);

        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendSurveyOptionClickEvent(encodedIdentifier, body);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }

    private void showBannerIfExists() {
        if (surveyModel.getBanner() != null) {
            rl_banner.setVisibility(View.VISIBLE);
            if (surveyModel.getBanner().getTargetUrl() != null) {
                iv_banner.setClickable(true);
                iv_banner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BannerFullPageClickBody body = new BannerFullPageClickBody();
                        body.setCampaignId(campaignId);
                        body.setBannerId(surveyModel.getBanner().getId());
                        body.setSurveyId(surveyModel.getId());
                        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendBannerFullPageClickEvent(encodedIdentifier, body);
                        call.enqueue(new Callback<CampaignRandomResponse>() {
                            @Override
                            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

                            }

                            @Override
                            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

                            }
                        });

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(surveyModel.getBanner().getTargetUrl()));
                        startActivity(i);
                        finish();
                    }
                });
            } else {
                iv_banner.setClickable(false);
            }
            iv_close_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            rl_banner.setVisibility(View.GONE);
            onBackPressed();
        }
    }

    private void goToNextQuestion() {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem + 1);
    }

    private boolean hasMoreQuestions() {
        return viewPager.getCurrentItem() != (pagerAdapter.getCount() - 1);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
