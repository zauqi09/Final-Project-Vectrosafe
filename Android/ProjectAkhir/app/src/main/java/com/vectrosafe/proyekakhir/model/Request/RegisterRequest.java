package com.vectrosafe.proyekakhir.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RegisterRequest {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("nama_lengkap")
    @Expose
    private String nama_lengkap;

    @SerializedName("tgl_lahir")
    @Expose
    private Date tgl_lahir;

    @SerializedName("no_hp")
    @Expose
    private String no_hp;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("password")
    @Expose
    private String password;

    public RegisterRequest(String username, String nama_lengkap, String password,Date tgl_lahir,String no_hp,String alamat) {
        this.setUsername(username);
        this.setPassword(password);
        this.setNama_lengkap(nama_lengkap);
        this.setTgl_lahir(tgl_lahir);
        this.setNo_hp(no_hp);
        this.setAlamat(alamat);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public Date getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(Date tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }


    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

}
