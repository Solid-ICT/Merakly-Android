package com.solidict.meraklysdk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solidict.meraklysdk.models.CampaignRandomObject;
import com.solidict.meraklysdk.models.CampaignRandomResponse;
import com.solidict.meraklysdk.models.OptionModel;
import com.solidict.meraklysdk.models.SurveyOptionClickEventBody;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SurveyFragment extends Fragment implements OptionsListItemClickListener {
    TextView tv_question;
    RecyclerView rv_options;
    SurveyFragmentCallbackListener listener;

    public SurveyFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_survey, container, false);
        tv_question = (TextView) view.findViewById(R.id.tv_question);
        rv_options = (RecyclerView) view.findViewById(R.id.rv_options);
        rv_options.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CampaignRandomObject questionModel = (CampaignRandomObject) getArguments().getSerializable(getContext().getString(R.string.argument_key_question));

        if (questionModel != null) {
            tv_question.setText(questionModel.getQuestion());
            OptionRecyclerViewAdapter adapter = new OptionRecyclerViewAdapter(questionModel.getOptions());
            rv_options.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }
    }

    public void setListener(SurveyFragmentCallbackListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    @Override
    public void onItemClick(int position, OptionModel optionModel) {

        listener.onSurveyCompleted(optionModel.getId());
    }
}
