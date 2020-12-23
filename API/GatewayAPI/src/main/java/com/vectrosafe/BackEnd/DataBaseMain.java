package com.vectrosafe.BackEnd;

import com.vectrosafe.BackEnd.RabbitMQ.*;

//Receive (RabbitMQ) -> MyBatisService -> Database -> Send (RabbitMQ)

class DataBaseMain {

    public static BackEndRecv receive;


    public static void main(String[] args) {
        try{
            receive = new BackEndRecv();
            System.out.println(" [*] Waiting for messages..");
            receive.addTrx();
            receive.getTrx();
            receive.getNasabah();
            receive.getAuth();
            receive.addNasabah();
            receive.getTelkomsel();
            receive.getXL();
            receive.buyVoucher();
            receive.login();
//            receive.verifyJWT();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}