package com.solidict.meraklysdk;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.solidict.meraklysdk.models.SurveyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emrahkucuk on 28/11/2017.
 */

public class SurveyViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> questions;

    public SurveyViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public void init(SurveyModel surveyModel, Context context, SurveyFragmentCallbackListener listener) {
        for (int i = 0; i < surveyModel.getQuestions().size(); i++) {
            SurveyFragment surveyFragment = new SurveyFragment();
            Bundle b = new Bundle();
            b.putSerializable(context.getString(R.string.argument_key_question),surveyModel.getQuestions().get(i));
            surveyFragment.setArguments(b);
            surveyFragment.setListener(listener);
            getQuestions().add(surveyFragment);
        }
    }

    public List<Fragment> getQuestions() {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        return questions;
    }


    @Override
    public Fragment getItem(int position) {
        return getQuestions().get(position);
    }

    @Override
    public int getCount() {
        return getQuestions().size();
    }
}
