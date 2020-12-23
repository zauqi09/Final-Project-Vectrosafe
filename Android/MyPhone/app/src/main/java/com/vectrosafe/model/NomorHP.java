package com.vectrosafe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NomorHP {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("no_hp")
    @Expose
    private String no_hp;

    @SerializedName("pulsa")
    @Expose
    private Long pulsa;

    @SerializedName("masa_aktif")
    @Expose
    private Date masa_aktif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public Long getPulsa() {
        return pulsa;
    }

    public void setPulsa(Long pulsa) {
        this.pulsa = pulsa;
    }

    public Date getMasa_aktif() {
        return masa_aktif;
    }

    public void setMasa_aktif(Date masa_aktif) {
        this.masa_aktif = masa_aktif;
    }
}
