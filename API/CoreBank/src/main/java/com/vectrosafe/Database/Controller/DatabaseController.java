package com.vectrosafe.Database.Controller;

import com.google.gson.Gson;
import com.vectrosafe.Database.Model.Auth;
import com.vectrosafe.Database.Model.Nasabah;
import com.vectrosafe.Database.Model.Transaksi;
import com.vectrosafe.Database.Model.TransaksiRequest;
import com.vectrosafe.Database.RabbitMQ.DatabaseSend;
import com.vectrosafe.Database.Service.AuthDAO;
import com.vectrosafe.Database.Service.NasabahDAO;
import com.vectrosafe.Database.Service.TransaksiDAO;
import okhttp3.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class DatabaseController {
    private final DatabaseSend send = new DatabaseSend();
    private EntityManager entityManager;
    private TransaksiDAO trxDao;
    private AuthDAO authDao;
    private NasabahDAO nasDao;
    public void connectJPA(){
        this.entityManager = Persistence
                .createEntityManagerFactory("user-unit")
                .createEntityManager();
        nasDao = new NasabahDAO(entityManager);
        trxDao = new TransaksiDAO(entityManager);
        authDao = new AuthDAO(entityManager);
        try {
            entityManager.getTransaction().begin();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }
    //    private Adapter adapter = new Adapter();
    public void commitJPA(){
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void addUser(String userString) throws Exception {
        connectJPA();
        String sendMessage = "Error";
        Auth userFound = new Gson().fromJson(userString, Auth.class);
        if(!authDao.isRegistered(userString)){
            authDao.addUserDAO(userString);
        }
        commitJPA();
        connectJPA();
        Auth userauth = authDao.findUsername(userFound.getUsername());
        if (userauth!=null){
            if(nasDao.findIdAuth(userauth.getId_auth().toString())==null){
                nasDao.addUserDAO(userauth.getId_auth(), new Gson().fromJson(userString, Nasabah.class));
                commitJPA();
                sendMessage = new Gson().toJson(userFound);
            }
        send.response_RegisUser(sendMessage);
        }
    }

    public void getTransaksi(String trxString) throws InterruptedException {
        String sendMessage = "Error";
        TransaksiRequest trx = new Gson().fromJson(trxString, TransaksiRequest.class);
        Long id_nasabah = trx.getId_nasabah();
        Date fromdate = trx.getFromDate();
        Date todate = trx.getToDate();
        System.out.println(id_nasabah);
        System.out.println(fromdate);
        System.out.println(todate);
        connectJPA();
        Nasabah nasabah = nasDao.findId(String.valueOf(trx.getId_nasabah()));
        if (nasabah!= null){
            List<Transaksi> trxList = trxDao.findTransaksi(id_nasabah,fromdate,todate);
            commitJPA();
            String strList = new Gson().toJson(trxList);
            System.out.println(strList);
            if (!trxList.isEmpty()){
                sendMessage = strList;
            }
        }
        send.response_getTrx(sendMessage);
    }

    public void addTransaksi(String trxString) throws InterruptedException, IOException {
        String sendMessage = "Error";
        Transaksi trx = new Gson().fromJson(trxString, Transaksi.class);
        System.out.println(trx.getId_nasabah());
        connectJPA();
        Nasabah nasabah = nasDao.findId(String.valueOf(trx.getId_nasabah()));
        if (nasabah!= null){
            trxDao.addTransaksi(trxString,nasabah.getSaldo());
            nasDao.doTrx(trxString);
            commitJPA();
            sendMessage = trxString;
        }
        send.response_addTrx(sendMessage);
    }

    public void getAuth(String userString) throws InterruptedException {
        connectJPA();
        String sendMessage = "Error";
        Auth user= authDao.findId(userString);
        commitJPA();
        if (user!= null){
            String usrString = new Gson().toJson(user);
            sendMessage = usrString;
        } send.response_getAuth(sendMessage);
    }

    public void getAuthbyUsername(String userString) throws InterruptedException {
        connectJPA();
        String sendMessage = "Error";
        System.out.println(userString);
        Auth user= authDao.findUsername(userString);
        commitJPA();
        if (user!= null){
            System.out.println(user.getUsername());
            String usrString = new Gson().toJson(user);
            sendMessage = usrString;
        }
        send.response_getAuthbyUsername(sendMessage);
    }

    public void getNasabah(String userString) throws InterruptedException {
        connectJPA();
        String sendMessage = "Error";
        Nasabah nasabah= nasDao.findIdAuth(userString);
        commitJPA();
        if (nasabah!= null){
            String usrString = new Gson().toJson(nasabah);
            sendMessage = usrString;
        }
        send.response_getNasabah(sendMessage);
    }
}
