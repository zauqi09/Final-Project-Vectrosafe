package com.vectrosafe.BackEnd.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.vectrosafe.BackEnd.Controller.DatabaseController;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class BackEndRecv {
    private final DatabaseController dbcon = new DatabaseController();
    public void connectRabbitMQ(String declare) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("user");
            factory.setPassword("iggamingcs");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(declare, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String deliver = new String(delivery.getBody(), StandardCharsets.UTF_8);
                switch (declare){
                    case "android_addTrx" :
                        try {
                            dbcon.addTransaksi(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "android_getTrx" :
                        try {
                            dbcon.getTransaksi(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "android_addNasabah" :
                        try {
                            dbcon.addUser(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "android_getAuth" :
                        try {
                            dbcon.getAuth(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "android_getNasabah" :
                        try {
                            dbcon.getNasabah(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "android_getXL" :
                        try {
                            dbcon.getXL();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "android_getTelkomsel" :
                        try {
                            dbcon.getTelkomsel();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "android_buyVoucher":
                        try {
                            dbcon.buyVoucher(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    case "android_loginNasabah":
                        try {
                            dbcon.login(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                    case "android_verifyJWT":
//                        try{
//                            dbcon.verifyJWT(deliver);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                }
            };
            channel.basicConsume(declare, true, deliverCallback, consumerTag -> {
            });

        } catch (Exception e) {
            System.out.println("Error processing data = " + e);
        }
    }

    public void addTrx() {
            connectRabbitMQ("android_addTrx");
    }


    public void getTrx() {
        connectRabbitMQ("android_getTrx");
    }


    public void addNasabah() {
        connectRabbitMQ("android_addNasabah");
    }

    public void getAuth() {
        connectRabbitMQ("android_getAuth");
    }

    public void getNasabah() {
        connectRabbitMQ("android_getNasabah");
    }

    public void getXL() {
        connectRabbitMQ("android_getXL");
    }

    public void getTelkomsel() {
        connectRabbitMQ("android_getTelkomsel");
    }

    public void buyVoucher() {
        connectRabbitMQ("android_buyVoucher");
    }

    public void login() {
        connectRabbitMQ("android_loginNasabah");
    }
//    public void verifyJWT() {
//        connectRabbitMQ("android_verifyJWT");
//    }

}
