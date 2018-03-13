package com.solidict.meraklysdk.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solidict.meraklysdk.R;
import com.solidict.meraklysdk.models.CampaignRandomObject;

import java.util.ArrayList;

/**
 * Created by muazekici on 10.03.2018.
 */

public class SurveyQuestionFragment extends Fragment {

    private CampaignRandomObject mQuestion;
    private int mPosition;
    private TextView mTvQuestion;
    private LinearLayout mLlContainer;
    private QuestionAnsweredListener mListener;
    private ArrayList<TextView> mAnswerList;
    private int mLastClicked = -1;

    public interface QuestionAnsweredListener{
        void onQuestionAnswered(int answerPos,int questionPos);
    }

    public SurveyQuestionFragment(){

    }

    public static SurveyQuestionFragment newInstance(CampaignRandomObject questionObj,int position){
        SurveyQuestionFragment fragment = new SurveyQuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("Question",questionObj);
        arguments.putSerializable("QuestionPosition",position);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = (CampaignRandomObject) getArguments().getSerializable("Question");
        mPosition = getArguments().getInt("QuestionPosition");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.survey_question_layout,container,false);
        mTvQuestion = v.findViewById(R.id.tv_survey_question);
        mTvQuestion.setText(mQuestion.getQuestion());
        mLlContainer = v.findViewById(R.id.ll_answers_container);
        setQuestionAnswers();
        return v;
    }

    private void setQuestionAnswers(){
        mAnswerList = new ArrayList<TextView>();
        for(int i = 0; i < mQuestion.getOptions().size() ; i++ ){
            TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.survey_answer_layout,mLlContainer,false);
            tv.setText(mQuestion.getOptions().get(i).getOption());
            final int index = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onQuestionAnswered(index,mPosition);

                        if(mLastClicked == index){
                            return;
                        }else if(mLastClicked == -1){
                            mAnswerList.get(index).setBackground(getResources().getDrawable(R.drawable.bg_segmented_clicked));
                            mLastClicked = index;
                        }else {
                            mAnswerList.get(mLastClicked).setBackground(getResources().getDrawable(R.drawable.bg_segmented_buttons));
                            mAnswerList.get(index).setBackground(getResources().getDrawable(R.drawable.bg_segmented_clicked));
                            mLastClicked = index;
                        }

                    }
                }
            });
            mAnswerList.add(tv);
            mLlContainer.addView(tv);
        }
    }

    public void setQuestionAnsweredListener(QuestionAnsweredListener listener){
        mListener = listener;
    }
}
