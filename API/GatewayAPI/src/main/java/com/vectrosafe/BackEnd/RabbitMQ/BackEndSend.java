package com.vectrosafe.BackEnd.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class BackEndSend {
    boolean isDone = false;
    public void connectRabbitMQ(String sendString, String declare){
        isDone = false;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("user");
        factory.setPassword("iggamingcs");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel() ){
            System.out.println("send_message : "+sendString);
            channel.queueDeclare(declare, false, false, false, null);
            channel.basicPublish("", declare, null, sendString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            System.out.println("Error : "+e);
        }
    }
    public void response_addTrx(String message) {
        connectRabbitMQ(message, "android_response_addTrx");
    }
    public void response_getTrx(String message) {
        connectRabbitMQ(message, "android_response_getTrx");
    }
    public void response_RegisUser(String message) {
        connectRabbitMQ(message, "android_response_RegisUser");
    }
    public void response_getAuth(String message) {
        connectRabbitMQ(message, "android_response_getAuth");
    }
    public void response_getNasabah(String message) { connectRabbitMQ(message, "android_response_getNasabah"); }
    public void response_getTelkomsel(String message) {
        connectRabbitMQ(message, "android_response_getTelkomsel");
    }
    public void response_getXL(String message) {
        connectRabbitMQ(message, "android_response_getXL");
    }
    public void response_buyVoucher(String message) { connectRabbitMQ(message, "android_response_buyVoucher"); }
    public void response_loginNasabah(String message) { connectRabbitMQ(message, "android_response_loginNasabah");}
//    public void response_verifyJWT(String message) { connectRabbitMQ(message, "android_response_verifyJWT");}
}
