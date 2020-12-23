package com.vectrosafe.BackEnd.Model;

import java.sql.Timestamp;

public class Transaksi {
    private Long id_transaksi;
    private Long no_transaksi;
    private Long id_nasabah;
    private String tipe;
    private Long mutasi;
    private Timestamp waktu;
    private String keterangan;
    private Long saldo;


    public Long getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(Long id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public Long getNo_transaksi() {
        return no_transaksi;
    }

    public void setNo_transaksi(Long no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    public Long getId_nasabah() {
        return id_nasabah;
    }

    public void setId_nasabah(Long id_nasabah) {
        this.id_nasabah = id_nasabah;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Timestamp getWaktu() {
        return waktu;
    }

    public void setWaktu(Timestamp waktu) {
        this.waktu = waktu;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Long getMutasi() {
        return mutasi;
    }

    public void setMutasi(Long mutasi) {
        this.mutasi = mutasi;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }
}
