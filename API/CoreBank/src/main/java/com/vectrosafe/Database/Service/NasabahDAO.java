package com.vectrosafe.Database.Service;

import com.google.gson.Gson;
import com.vectrosafe.Database.Model.Nasabah;
import com.vectrosafe.Database.Model.Transaksi;
import okhttp3.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class NasabahDAO {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    public NasabahDAO() {
    }
    public NasabahDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    private final AtomicLong LAST_TIME_MS = new AtomicLong();
    public long uniqueCurrentTimeMS() {
        long now = System.currentTimeMillis();
        while(true) {
            long lastTime = LAST_TIME_MS.get();
            if (lastTime >= now)
                now = lastTime+1;
            if (LAST_TIME_MS.compareAndSet(lastTime, now))
                return now;
        }
    }



    public void addUserDAO(Long id, Nasabah nasabah) {
        Nasabah newNasabah = new Nasabah();
        newNasabah.setNama_lengkap(nasabah.getNama_lengkap());
        newNasabah.setNo_hp(nasabah.getNo_hp());
        newNasabah.setTgl_lahir(nasabah.getTgl_lahir());
        newNasabah.setAlamat(nasabah.getAlamat());
        newNasabah.setId_auth(id);
        newNasabah.setNo_rekening(String.valueOf(uniqueCurrentTimeMS()));
        newNasabah.setSaldo((long) 0);
        entityManager.persist(newNasabah);
        //commitTransaction();
    }

    public void infoSaldo(Long id){

    }

    public Nasabah findId(String idString) {
        Nasabah nasabah;
        try {
            nasabah = entityManager.createQuery("SELECT a FROM Nasabah a where a.id_nasabah ="+idString, Nasabah.class).getSingleResult();
        } catch (NoResultException e){
            nasabah = null;
        }
        return nasabah;
    }

    public Nasabah findIdAuth(String idString) {
        Nasabah nasabah;
        try {
            nasabah = entityManager.createQuery("SELECT a FROM Nasabah a where a.id_auth ="+idString, Nasabah.class).setMaxResults(1).getSingleResult();
        } catch (NoResultException e){
            nasabah = null;
        }
        return nasabah;
    }

    public void doTrx(String trxString) throws IOException {
        Transaksi trx = new Gson().fromJson(trxString, Transaksi.class);
        Nasabah nasabah = findId(String.valueOf(trx.getId_nasabah()));
        if (nasabah!=null){
            if (trx.getTipe().equals("Debit")){
                Long saldo = nasabah.getSaldo() + trx.getMutasi();
                nasabah.setSaldo(saldo);
            } else if (trx.getTipe().equals("Kredit")){
                Long saldo = nasabah.getSaldo() - trx.getMutasi();
                nasabah.setSaldo(saldo);
            }
        }
    }

}
