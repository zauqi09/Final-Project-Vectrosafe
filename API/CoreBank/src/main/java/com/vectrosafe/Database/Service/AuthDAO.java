package com.vectrosafe.Database.Service;

import com.google.gson.Gson;
import com.vectrosafe.Database.Model.Auth;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.security.spec.KeySpec;
import java.util.List;


public class AuthDAO {
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    public AuthDAO() {
    }
    public AuthDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }
    public void addUserDAO(String userString) throws Exception {
        Auth usernew = new Gson().fromJson(userString, Auth.class);
        usernew.setPassword(new Encrypt().encrypt(usernew.getPassword()));
        entityManager.persist(usernew);
        //commitTransaction();
    }


    public Auth findUsername(String userString) {
        Auth mhs;
        try {
            mhs = entityManager.createQuery("SELECT a FROM Auth a where a.username ='"+userString+"'", Auth.class).getSingleResult();
        } catch (NoResultException e){
            System.out.println("no Result");
            mhs = null;

        }
        return mhs;
    }



    public List<Auth> findAll() {
        List<Auth> listUser = entityManager.createQuery("SELECT a FROM Auth a ", Auth.class).getResultList();
        return listUser;
    }

    public Auth findId(String idString) {
        Auth mhs;
        try {
            mhs = entityManager.createQuery("SELECT a FROM Auth a where a.id_auth ="+idString, Auth.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
        System.out.println("debug"+mhs);
        return mhs;
    }


    public boolean isRegistered(String userString) {
        List<Auth> listAllUser = findAll();
        Auth user = new Gson().fromJson(userString, Auth.class);
        boolean registered = false;
        for (Auth obj : listAllUser){
            if(obj.getUsername().equals(user.getUsername())){
                registered = true;
            }
        }
        return registered;
    }


}
