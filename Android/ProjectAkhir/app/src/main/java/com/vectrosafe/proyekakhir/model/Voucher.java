package com.vectrosafe.proyekakhir.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Voucher {
    @SerializedName("id_produk")
    @Expose
    private Long id_produk;

    @SerializedName("nama_produk")
    @Expose
    private String nama_produk;

    @SerializedName("harga_produk")
    @Expose
    private Long harga_produk;

    @SerializedName("operator")
    @Expose
    private String operator;

    public Long getId_produk() {
        return id_produk;
    }

    public void setId_produk(Long id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public Long getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(Long harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
