package com.vectrosafe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRequest {

    @SerializedName("no_hp")
    @Expose
    private String no_hp;

    @SerializedName("operator")
    @Expose
    private String operator;

    public GetRequest(String no_hp, String operator){
        setNo_hp(no_hp);
        setOperator(operator);
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
