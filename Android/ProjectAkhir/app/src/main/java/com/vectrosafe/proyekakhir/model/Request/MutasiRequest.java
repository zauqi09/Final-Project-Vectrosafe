package com.vectrosafe.proyekakhir.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MutasiRequest {
    @SerializedName("id_nasabah")
    @Expose
    private Long id_nasabah;

    @SerializedName("tgl_awal")
    @Expose
    private String operator;

    @SerializedName("id_produk")
    @Expose
    private Long id_produk;

    @SerializedName("no_hp")
    @Expose
    private String no_hp;

    public MutasiRequest(Long id_nasabah, String operator, Long id_produk, String no_hp){
        setId_nasabah(id_nasabah);
        setOperator(operator);
        setId_produk(id_produk);
        setNo_hp(no_hp);
    }

    public Long getId_nasabah() {
        return id_nasabah;
    }

    public void setId_nasabah(Long id_nasabah) {
        this.id_nasabah = id_nasabah;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getId_produk() {
        return id_produk;
    }

    public void setId_produk(Long id_produk) {
        this.id_produk = id_produk;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }
}
