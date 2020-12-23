package com.vectrosafe.Database.Service;

import com.google.gson.Gson;
import com.vectrosafe.Database.Model.Transaksi;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TransaksiDAO {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    public TransaksiDAO() {
    }
    public TransaksiDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public void addTransaksi(String trxString, Long saldo){
        System.out.println(trxString);
        Transaksi trx = new Gson().fromJson(trxString, Transaksi.class);
        System.out.println(trx.getTipe());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        trx.setWaktu(timestamp);
        trx.setNo_transaksi(timestamp.getTime());
        if (trx.getTipe().equals("Debit")){
            trx.setSaldo(saldo + trx.getMutasi());
        } else if (trx.getTipe().equals("Kredit")){
            trx.setSaldo(saldo - trx.getMutasi());
        }
        entityManager.persist(trx);
    }

    public List<Transaksi> findTransaksi(Long id_nasabah, Date fromdate, Date todate) {
        List<Transaksi> trx;
        try {
            trx = entityManager.createQuery("SELECT a FROM Transaksi a where a.id_nasabah=:id_nsb AND a.waktu BETWEEN :startDate AND :endDate", Transaksi.class)
                    .setParameter("id_nsb", id_nasabah)
                    .setParameter("startDate", fromdate)
                    .setParameter("endDate", todate).getResultList();
        } catch (NoResultException e){
            return null;
        }
        System.out.println("debug : "+trx);
        return trx;
    }

    public Transaksi findId(String idString) {
        Transaksi trx;
        try {
            trx = entityManager.createQuery("SELECT a FROM Transaksi a where a.id_transaksi ="+idString, Transaksi.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
        System.out.println("debug : "+trx);
        return trx;
    }
}
