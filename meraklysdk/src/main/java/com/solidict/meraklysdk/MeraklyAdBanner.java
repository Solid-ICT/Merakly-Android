package com.solidict.meraklysdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;
import com.solidict.meraklysdk.BuildConfig;
import com.solidict.meraklysdk.custom_views.SegmentedButtons;
import com.solidict.meraklysdk.models.DeviceDetails;
import com.solidict.meraklysdk.models.IdentifierModel;
import com.solidict.meraklysdk.network.NetworkHandler;
import com.solidict.meraklysdk.R;
import com.solidict.meraklysdk.fragments.SurveyFragment;
import com.solidict.meraklysdk.models.BannerInlineClickBody;
import com.solidict.meraklysdk.models.CampaignOptionClickBody;
import com.solidict.meraklysdk.models.CampaignRandomResponse;
import com.solidict.meraklysdk.models.OptionModel;
import com.solidict.meraklysdk.models.SendSkipEventRequestBody;
import com.solidict.meraklysdk.models.SendViewEventRequestBody;
import com.solidict.meraklysdk.models.SurveyModel;
import com.solidict.meraklysdk.utils.GenderType;
import com.solidict.meraklysdk.utils.OsType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by muazekici on 9.03.2018.
 */

public class MeraklyAdBanner extends RelativeLayout implements SegmentedButtons.SegmentedItemClicked, SurveyFragment.SurveyResultListener {

    private TextView mQuestion;
    private SegmentedButtons mChoices;
    private String mEncodedIdentifier;
    private RelativeLayout mLoading,mBanner;
    private ImageView mBannerImage,mBannerClose,mRecycleQuestion;
    private CampaignRandomResponse mResponse;
    private String mUserAdvertId;
    private long mCampaignStartTime;
    private Fragment surveyDialog;

    public MeraklyAdBanner(Context context) {
        super(context);
        init();
    }

    public MeraklyAdBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeraklyAdBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.banner_view_layout,this);
        mChoices = findViewById(R.id.segmented_buttons);
        mQuestion = findViewById(R.id.tv_banner_question);
        mBanner = findViewById(R.id.rl_campaign_banner);
        mBannerImage = findViewById(R.id.iv_campaign_banner);
        mBannerClose = findViewById(R.id.iv_campaign_banner_close);
        mRecycleQuestion = findViewById(R.id.iv_recycyle_question);
        mRecycleQuestion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSkipEvent();
                load();
            }
        });
        mChoices.setClickListener(this);
        mLoading = findViewById(R.id.rl_loading);

    }

    public void setCredentials(final String apiKey, final String secretKey){


        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext().getApplicationContext());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return advertId;
            }
            @Override
            protected void onPostExecute(String advertId) {

                mUserAdvertId = advertId;
                mLoading.setVisibility(GONE);
                createIdentifierModel(apiKey,secretKey);
            }

            @Override
            protected void onPreExecute() {
                mLoading.setVisibility(VISIBLE);
            }
        };
        task.execute();

    }

    private void createIdentifierModel(String apiKey, String secretKey){
        IdentifierModel identifierModel = new IdentifierModel();
        identifierModel.setApiKey(apiKey);
        identifierModel.setSecretKey(secretKey);

        /*WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();

        String mac = "r35trgbct54";*/
        //        identifierModel.setDeviceId(android.provider.Settings.System.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        identifierModel.setDeviceId(mUserAdvertId);
        identifierModel.setVersion(BuildConfig.VERSION_CODE);
        identifierModel.setLocale(getResources().getConfiguration().locale.getLanguage());
        identifierModel.setOsType(OsType.ANDROID.getValue());
        identifierModel.setOsVersion(Build.VERSION.RELEASE);

        Gson gson = new Gson();
        String json = gson.toJson(identifierModel);

        mEncodedIdentifier = Base64.encodeToString(json.getBytes(), Base64.NO_WRAP);

        //sendUserDetail(25,GenderType.MAN);

        load();
    }

    public void load() {
        mQuestion.setVisibility(INVISIBLE);
        mChoices.setVisibility(INVISIBLE);
        mLoading.setVisibility(VISIBLE);
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().getCampaignRandom(mEncodedIdentifier);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {
                mResponse = response.body();

                if (mResponse != null && mResponse.isSucceed()) {
                    configureOptions();
                } else if(mResponse != null &&!mResponse.isSucceed() && mResponse.getObject() == null){
                    mQuestion.setText(mResponse.getMessage());
                    mQuestion.setVisibility(VISIBLE);
                    //bannerListener.onLoadError(campaignRandomResponse.getMessage());
                } else {
                    mQuestion.setVisibility(INVISIBLE);
                }

                mLoading.setVisibility(GONE);

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {
                mLoading.setVisibility(GONE);
            }
        });
    }



    @Override
    public void onItemClicked(int position) {
        final OptionModel clickedModel = mResponse.getObject().getOptions().get(position);
        sendCampaignOptionClickEvent(clickedModel.getId(),System.currentTimeMillis());

        if(clickedModel.getSurvey() != null){
            openSurvey(clickedModel.getSurvey(),mResponse.getObject().getId(),clickedModel.getId());

        }else if(clickedModel.getBanner() != null ){
            Picasso.with(getContext()).load(clickedModel.getBanner().getImageUrl()).fit().into(mBannerImage);
            mBanner.setVisibility(VISIBLE);

            if(clickedModel.getBanner().getTargetUrl() != null ){
                mBannerImage.setClickable(false);
                mBannerImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickedBannerEventRequest(clickedModel.getId(),clickedModel.getBanner().getId());

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(clickedModel.getBanner().getTargetUrl()));
                        getContext().startActivity(i);
                    }
                });
            }else{
                mBannerImage.setClickable(false);
            }

            mBannerClose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBanner.setVisibility(GONE);
                    load();
                }
            });

        }else{
            mQuestion.setText("Teşekkürler");
            mQuestion.setVisibility(VISIBLE);
            mChoices.setVisibility(INVISIBLE);

        }





    }


    @Override
    public void onSurveyFinishSuccess() {
        load();
    }

    @Override
    public void onSurveyFinishFail() {

    }

    private void configureOptions(){
        if(mResponse != null){
            ArrayList<String> optionTexts = new ArrayList<String>();

            for(OptionModel o : mResponse.getObject().getOptions()){
                optionTexts.add(o.getOption());
            }

            mChoices.setButtonTexts(optionTexts);
            mQuestion.setText(mResponse.getObject().getQuestion());

            mChoices.setVisibility(VISIBLE);
            mQuestion.setVisibility(VISIBLE);

            mCampaignStartTime = System.currentTimeMillis();

            sendViewEventRequest(mResponse.getObject().getId());
        }
    }

    private void sendViewEventRequest(int campaignId){
        SendViewEventRequestBody sendViewEventRequestBody = new SendViewEventRequestBody();
        sendViewEventRequestBody.setCampaignId(campaignId);
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendViewEvent(mEncodedIdentifier, sendViewEventRequestBody);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }

    private void openSurvey(SurveyModel sModel,int cId, int cOptionId){
        FragmentManager fm = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        SurveyFragment fr = (SurveyFragment) fm.findFragmentByTag("MERAKLY_SURVEY_DIALOG");
        if(fr == null) {
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            fr = SurveyFragment.newInstance(sModel, mEncodedIdentifier, cId, cOptionId);
            fr.setSurveyResultListener(this);
            fr.show(ft, "MERAKLY_SURVEY_DIALOG");
        }
    }


    private void clickedBannerEventRequest(int campaignOptionId, int bannerId){
        BannerInlineClickBody body = new BannerInlineClickBody();
        body.setBannerId(bannerId);
        body.setCampaignOptionId(campaignOptionId);

        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendBannerInlineClickEvent(mEncodedIdentifier, body);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }


    private void sendCampaignOptionClickEvent(int optionId,long clickTime) {
        CampaignOptionClickBody body = new CampaignOptionClickBody();
        body.setReplyTime((1f*(clickTime-mCampaignStartTime)/1000f));
        body.setCampaignOptionId(optionId);
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendCampaignOptionClickEvent(mEncodedIdentifier, body);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }


    private void sendSkipEvent(){

        if(mResponse.getObject() == null){
            return;
        }

        SendSkipEventRequestBody sendSkipEventRequestBody = new SendSkipEventRequestBody();
        sendSkipEventRequestBody.setCampaignId(mResponse.getObject().getId());
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendSkipEvent(mEncodedIdentifier, sendSkipEventRequestBody);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }


    public void sendUserDetail(int age, GenderType type){
        DeviceDetails details = new DeviceDetails();
        details.setAge(age);
        details.setGender(type.getValue());
        if(mEncodedIdentifier == null){
            throw new RuntimeException("credentials are not setted");
        }
        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendDeviceDetailsEvent(mEncodedIdentifier,details);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
