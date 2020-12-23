package com.vectrosafe.proyekakhir.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectrosafe.proyekakhir.model.Nasabah;
import com.vectrosafe.proyekakhir.model.Voucher;

import java.util.List;

public class VoucherResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("data")
    @Expose
    private List<Voucher> data = null;

    public VoucherResponse(){}
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

    public List<Voucher> getData() {
        return data;
    }

    public void setData(List<Voucher> data) {
        this.data = data;
    }
}
