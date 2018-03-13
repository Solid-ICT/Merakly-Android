package com.solidict.meraklysdk.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.solidict.meraklysdk.network.NetworkHandler;
import com.solidict.meraklysdk.R;
import com.solidict.meraklysdk.adapters.SurveyQuestionPagerAdapter;
import com.solidict.meraklysdk.models.BannerFullPageClickBody;
import com.solidict.meraklysdk.models.CampaignRandomObject;
import com.solidict.meraklysdk.models.CampaignRandomResponse;
import com.solidict.meraklysdk.models.SurveyModel;
import com.solidict.meraklysdk.models.SurveyOptionClickEventBody;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by muazekici on 10.03.2018.
 */

public class SurveyFragment extends DialogFragment {


    public interface SurveyResultListener{
        void onSurveyFinishSuccess();
        void onSurveyFinishFail();
    }

    private SurveyResultListener mListener;
    private SurveyModel mSurveyModel;
    private String mEncodedIdentifier;
    private int mCampaignId;
    private int mCampaignOptionId;
    private ViewPager mQuestionPager;
    private CircleIndicator mPagerIndicator;
    private SurveyQuestionPagerAdapter mPagerAdapter;
    private RelativeLayout mBanerContainer;
    private ImageView mBannerView, mBannerClose;
    List<CampaignRandomObject> objList;


    public SurveyFragment(){

    }

    public static SurveyFragment newInstance(SurveyModel sModel, String identifier, int cId, int cOptionId){
        SurveyFragment fragment = new SurveyFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable("SurveyModel",sModel);
        arguments.putString("Identifier",identifier);
        arguments.putInt("CampaignId",cId);
        arguments.putInt("CampaignOptionId",cOptionId);

        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for full screen view
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
        //setStyle(DialogFragment.STYLE_NO_TITLE,R.style.AppDialogTheme);
        setRetainInstance(true);

        mSurveyModel = (SurveyModel) getArguments().getSerializable("SurveyModel");
        mEncodedIdentifier = getArguments().getString("Identifier");
        mCampaignId = getArguments().getInt("CampaignId");
        mCampaignOptionId = getArguments().getInt("CampaignOptionId");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_survey,container,false);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().setCanceledOnTouchOutside(false);
        mQuestionPager = v.findViewById(R.id.vp_survey_questions);
        mPagerIndicator = v.findViewById(R.id.indicator_pager);
        mPagerAdapter = new SurveyQuestionPagerAdapter(getChildFragmentManager());

        createData();

        mPagerAdapter.initializeQuestionFragments(objList, new SurveyQuestionFragment.QuestionAnsweredListener() {
            @Override
            public void onQuestionAnswered(int answerPos, int questionPos) {
                changeToNextQuestion(questionPos,answerPos);
            }
        });

        mQuestionPager.setAdapter(mPagerAdapter);
        mPagerIndicator.setViewPager(mQuestionPager);
        mPagerAdapter.registerDataSetObserver(mPagerIndicator.getDataSetObserver());

        View back = v.findViewById(R.id.iv_back_survey);
        mBanerContainer = v.findViewById(R.id.rl_survey_banner);
        mBannerView = v.findViewById(R.id.iv_survey_banner);
        mBannerClose = v.findViewById(R.id.iv_survey_banner_close);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToPrevQuestion();
            }
        });

        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if(mListener != null ){
            mListener.onSurveyFinishSuccess();
        }
    }

    public void setSurveyResultListener(SurveyResultListener listener){
        mListener = listener;
    }


    private void createData(){
        objList = new ArrayList<CampaignRandomObject>();

        for(CampaignRandomObject o : mSurveyModel.getQuestions()){
            objList.add(o);
        }

    }

    private void changeToNextQuestion(int questionPos, int answerPos){
        int pos = mQuestionPager.getCurrentItem();
        if(pos+1 == objList.size()){
            showSurveyBanner();
        }else{
            sendOptionClickEvent(mSurveyModel.getQuestions().get(questionPos).getOptions().get(answerPos).getId());
            mQuestionPager.setCurrentItem(pos+1);
        }

    }

    private void showSurveyBanner(){
        if(mSurveyModel.getBanner() != null){

            mBanerContainer.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(mSurveyModel.getBanner().getImageUrl()).fit().into(mBannerView);

            if(mSurveyModel.getBanner().getTargetUrl() != null){
                mBannerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                BannerFullPageClickBody body = new BannerFullPageClickBody();
                body.setCampaignId(mCampaignId);
                body.setBannerId(mSurveyModel.getBanner().getId());
                body.setSurveyId(mSurveyModel.getId());
                Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendBannerFullPageClickEvent(mEncodedIdentifier, body);
                call.enqueue(new Callback<CampaignRandomResponse>() {
                    @Override
                    public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

                    }
                });

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mSurveyModel.getBanner().getTargetUrl()));
                startActivity(i);
                dismiss();
                    }
                });

            }else{
                mBannerView.setClickable(false);
            }

            mBannerClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }else{
            dismiss();
        }
    }

    private void sendOptionClickEvent(int surveyOptionId){
        SurveyOptionClickEventBody body = new SurveyOptionClickEventBody();
        body.setCampaignId(mCampaignId);
        body.setCampaignOptionId(mCampaignOptionId);
        body.setSurveyOptionId(surveyOptionId);

        Call<CampaignRandomResponse> call = NetworkHandler.getInstance().sendSurveyOptionClickEvent(mEncodedIdentifier, body);
        call.enqueue(new Callback<CampaignRandomResponse>() {
            @Override
            public void onResponse(Call<CampaignRandomResponse> call, Response<CampaignRandomResponse> response) {

            }

            @Override
            public void onFailure(Call<CampaignRandomResponse> call, Throwable t) {

            }
        });
    }

    private void changeToPrevQuestion(){
        int pos = mQuestionPager.getCurrentItem();
        if(pos-1 == -1){
            return;
        }else{
            mQuestionPager.setCurrentItem(pos-1);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("retainedSurveyFragment",1);

    }
}
