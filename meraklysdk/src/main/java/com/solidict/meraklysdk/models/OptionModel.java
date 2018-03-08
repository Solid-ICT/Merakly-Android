package com.solidict.meraklysdk.models;

import java.io.Serializable;

/**
 * Created by emrahkucuk on 24/11/2017.
 */

public class OptionModel implements Serializable {
    int id;
    String option;
    SurveyModel survey;
    BannerModel banner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public SurveyModel getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyModel survey) {
        this.survey = survey;
    }

    public BannerModel getBanner() {
        return banner;
    }

    public void setBanner(BannerModel banner) {
        this.banner = banner;
    }
}
