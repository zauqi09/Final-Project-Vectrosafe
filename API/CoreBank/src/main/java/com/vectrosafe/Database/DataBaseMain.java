package com.vectrosafe.Database;

//Receive (RabbitMQ) -> MyBatisService -> Database -> Send (RabbitMQ)

import com.vectrosafe.Database.RabbitMQ.DatabaseRecv;

class DataBaseMain {

    public static DatabaseRecv receive = new DatabaseRecv();

    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages..");
            receive.addTrx();
            receive.addNasabah();
            receive.getAuth();
            receive.getTrx();
            receive.getNasabah();
            receive.getAuthByUsername();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}