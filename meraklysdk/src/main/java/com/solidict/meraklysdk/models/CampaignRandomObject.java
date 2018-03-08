package com.solidict.meraklysdk.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emrahkucuk on 24/11/2017.
 */

public class CampaignRandomObject implements Serializable {
    int id;
    String question;
    List<OptionModel> options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<OptionModel> getOptions() {
        return options;
    }

    public void setOptions(List<OptionModel> options) {
        this.options = options;
    }
}
