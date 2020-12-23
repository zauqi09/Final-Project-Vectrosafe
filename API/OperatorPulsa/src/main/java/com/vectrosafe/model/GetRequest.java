package com.vectrosafe.model;


public class GetRequest {
    private String no_hp;
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
