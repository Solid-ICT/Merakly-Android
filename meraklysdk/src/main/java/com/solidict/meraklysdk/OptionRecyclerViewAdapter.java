package com.solidict.meraklysdk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solidict.meraklysdk.models.CampaignRandomObject;
import com.solidict.meraklysdk.models.CampaignRandomResponse;
import com.solidict.meraklysdk.models.OptionModel;

import java.util.List;

/**
 * Created by emrahkucuk on 29/11/2017.
 */

class OptionRecyclerViewAdapter extends RecyclerView.Adapter<OptionRecyclerViewAdapter.ViewHolder>{
    List<OptionModel> options;
    private SurveyFragment onItemClickListener;

    public OptionRecyclerViewAdapter(List<OptionModel> options) {
        this.options = options;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.options_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_option.setText(options.get(position).getOption());
        holder.tv_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position, options.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public void setOnItemClickListener(SurveyFragment onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_option;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_option = itemView.findViewById(R.id.tv_option);
        }
    }
}
