package com.solidict.meraklysdk.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.solidict.meraklysdk.fragments.SurveyQuestionFragment;
import com.solidict.meraklysdk.models.CampaignRandomObject;
import com.solidict.meraklysdk.models.SurveyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muazekici on 10.03.2018.
 */

public class SurveyQuestionPagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> mFragmentList;

    public SurveyQuestionPagerAdapter(FragmentManager fm){
        super(fm);
        mFragmentList = new ArrayList<Fragment>();
    }

    public void initializeQuestionFragments(List<CampaignRandomObject> questionList, SurveyQuestionFragment.QuestionAnsweredListener listener){
        for(int i = 0; i < questionList.size(); i++ ){

            SurveyQuestionFragment fr = SurveyQuestionFragment.newInstance(questionList.get(i),i);
            fr.setQuestionAnsweredListener(listener);
            mFragmentList.add(fr);

        }
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }
}
