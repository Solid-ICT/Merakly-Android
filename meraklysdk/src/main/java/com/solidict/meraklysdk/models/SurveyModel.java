package com.solidict.meraklysdk.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emrahkucuk on 27/11/2017.
 */

public class SurveyModel implements Serializable{
    int id;
    List<CampaignRandomObject> questions;
    BannerModel banner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CampaignRandomObject> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CampaignRandomObject> questions) {
        this.questions = questions;
    }

    public BannerModel getBanner() {
        return banner;
    }

    public void setBanner(BannerModel banner) {
        this.banner = banner;
    }
}
