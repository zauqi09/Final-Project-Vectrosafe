package com.vectrosafe.API.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class RestApiRecv {
    private String messageRecv_resp;
    public String connectRabbitMQ(String declare) {
        messageRecv_resp = "";
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
                System.out.println("RabbitMQ message : " + deliver + " Queue : " + declare);
                messageRecv_resp = deliver;
            };
            channel.basicConsume(declare, true, deliverCallback, consumerTag -> {
            });
            while (messageRecv_resp.equals("")) {
                TimeUnit.MILLISECONDS.sleep(10);
            }
            return messageRecv_resp;
        } catch (Exception e) {
            System.out.println("Error processing data = " + e);
            return "none";
        }
    }
}
