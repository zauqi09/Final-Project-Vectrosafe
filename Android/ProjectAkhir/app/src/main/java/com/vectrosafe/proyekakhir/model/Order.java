package com.vectrosafe.proyekakhir.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id_order")
    @Expose
    private Long id_order;

    @SerializedName("id_nasabah")
    @Expose
    private Long id_nasabah;

    @SerializedName("operator")
    @Expose
    private String operator;

    @SerializedName("id_produk")
    @Expose
    private Long id_produk;

    @SerializedName("no_hp")
    @Expose
    private String no_hp;
    public Order() {}

    public Long getId_order() {
        return id_order;
    }

    public void setId_order(Long id_order) {
        this.id_order = id_order;
    }

    public Long getId_nasabah() {
        return id_nasabah;
    }

    public void setId_nasabah(Long id_nasabah) {
        this.id_nasabah = id_nasabah;
    }

    public Long getId_produk() {
        return id_produk;
    }

    public void setId_produk(Long id_produk) {
        this.id_produk = id_produk;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }
}
