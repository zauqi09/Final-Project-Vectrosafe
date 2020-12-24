package com.vectrosafe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectrosafe.model.NomorHP;

public class ApiResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("data")
    @Expose
    private NomorHP data = null;

    public ApiResponse(){}
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NomorHP getData() {
        return data;
    }

    public void setData(NomorHP data) {
        this.data = data;
    }
}
