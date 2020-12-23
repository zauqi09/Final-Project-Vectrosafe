package com.vectrosafe.Database.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class DatabaseSend {
    public static void connectRabbitMQ(String sendString, String declare){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("user");
        factory.setPassword("iggamingcs");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel() ){
            System.out.println("message : "+sendString);
            channel.queueDeclare(declare, false, false, false, null);
            channel.basicPublish("", declare, null, sendString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            System.out.println("Error : "+e);
        }
    }
    public void response_addTrx(String message) {
        connectRabbitMQ(message, "response_addTrx");
    }
    public void response_getTrx(String message) {
        connectRabbitMQ(message, "response_getTrx");
    }
    public void response_RegisUser(String message) {
        connectRabbitMQ(message, "response_RegisUser");
    }
    public void response_getAuth(String message) { connectRabbitMQ(message, "response_getAuth"); }
    public void response_getAuthbyUsername(String message) {
        connectRabbitMQ(message, "response_getAuthByUsername");
    }
    public void response_getNasabah(String message) { connectRabbitMQ(message, "response_getNasabah"); }
}
