package com.vectrosafe.API.RabbitMQ;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;


public class RestApiSend {
    public static void connectRabbitMQ(String sendString, String declare){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("user");
        factory.setPassword("iggamingcs");
        try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel() ){
            channel.queueDeclare(declare, false, false, false, null);
            channel.basicPublish("", declare, null, sendString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Bean
    public static void addTrx(String trxString){ connectRabbitMQ(trxString,"addTrx"); }

    @Bean
    public static void getTrx(String mutasi){ connectRabbitMQ(mutasi,"getTrx"); }

    @Bean
    public static void addNasabah(String trxString){ connectRabbitMQ(trxString,"addNasabah"); }

    @Bean
    public static void getAuthByUsername(String trxString){ connectRabbitMQ(trxString,"getAuthByUsername"); }

    @Bean
    public static void getAuth(String trxString){ connectRabbitMQ(trxString,"getAuth"); }

    @Bean
    public static void getNasabah(String trxString){ connectRabbitMQ(trxString,"getNasabah"); }
}

