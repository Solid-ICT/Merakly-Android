package com.solidict.meraklysdk.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solidict.meraklysdk.R;

import java.util.ArrayList;

/**
 * Created by muazekici on 9.03.2018.
 */

public class SegmentedButtons extends LinearLayout {



    public interface SegmentedItemClicked{
        void onItemClicked(int position);
    }

    private ArrayList<TextView> textViewList;
    private ArrayList<View> separatorList;

    private SegmentedItemClicked mListener;

    public SegmentedButtons(Context context) {
        super(context);
        init();
    }

    public SegmentedButtons(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SegmentedButtons(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

   private void init(){
        inflate(getContext(),R.layout.segmented_buttons_layout,this);
        textViewList = new ArrayList<TextView>();
        separatorList = new ArrayList<View>();
        this.setBackground(getResources().getDrawable(R.drawable.bg_segmented_buttons));
        /*this.setPadding((int)getResources().getDimension(R.dimen.segmented_button_padding),0,
                (int)getResources().getDimension(R.dimen.segmented_button_padding),0);*/
        for(int i = 0 ; i < 7 ; i++ ){
            if(i%2 == 0) {
                textViewList.add((TextView) this.getChildAt(i));
            }else{
                separatorList.add(this.getChildAt(i));
            }
        }

        /*ArrayList<String> ss = new ArrayList<String>();
       ss.add("test1");
       ss.add("test2");

       setButtonTexts(ss);*/
       setListenersOfButtons();
   }

   public void setButtonTexts(ArrayList<String> chooses){
       int count = chooses.size();

       for(int i = 0 ; i < textViewList.size() ;i++ ){
           if(i<count) {
               textViewList.get(i).setText(chooses.get(i));
               textViewList.get(i).setVisibility(VISIBLE);
           }else{
               textViewList.get(i).setVisibility(GONE);
           }
       }

       for(int i = 0 ; i < separatorList.size() ;i++ ){
           if(i<count-1) {
               separatorList.get(i).setVisibility(VISIBLE);
           }else{
               separatorList.get(i).setVisibility(GONE);
           }
       }

   }

   public void setClickListener(SegmentedItemClicked listener){
       this.mListener = listener;
   }

   private void setListenersOfButtons(){
        for(int i = 0 ; i < textViewList.size() ; i++ ){
            textViewList.get(i).setTag(i);
            textViewList.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onItemClicked((Integer) v.getTag());
                    }
                }
            });
        }
   }





}
