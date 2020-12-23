package com.vectrosafe.proyekakhir.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectrosafe.proyekakhir.model.Nasabah;

public class ApiResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("data")
    @Expose
    private Nasabah data;

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

    public Nasabah getData() {
        return data;
    }

    public void setData(Nasabah data) {
        this.data = data;
    }
}
