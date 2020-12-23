package com.vectrosafe.Database.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.vectrosafe.Database.Controller.DatabaseController;

import java.nio.charset.StandardCharsets;

public class DatabaseRecv {
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
                    case "addTrx" :
                        try {
                            dbcon.addTransaksi(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "getTrx" :
                        try {
                            dbcon.getTransaksi(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "addNasabah" :
                        try {
                            dbcon.addUser(deliver);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "getAuth" :
                        try {
                            dbcon.getAuth(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "getAuthByUsername" :
                        try {
                            dbcon.getAuthbyUsername(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "getNasabah" :
                        try {
                            dbcon.getNasabah(deliver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            };
            channel.basicConsume(declare, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error processing data = " + e);
        }
    }
    public void getTrx() {
        connectRabbitMQ("getTrx");
    }

    public void addTrx() {
            connectRabbitMQ("addTrx");
    }

    public void addNasabah() {
        connectRabbitMQ("addNasabah");
    }

    public void getAuthByUsername() {
        connectRabbitMQ("getAuthByUsername");
    }

    public void getAuth() {
        connectRabbitMQ("getAuth");
    }

    public void getNasabah() {
        connectRabbitMQ("getNasabah");
    }

}
