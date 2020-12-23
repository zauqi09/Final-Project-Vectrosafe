package com.vectrosafe.API.RabbitMQ;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public class RestApiSend{
    public static void connectRabbitMQ(String sendString, String declare) {
        ConnectionFactory factory = new ConnectionFactory();
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) uri = "amqp://user:iggamingcs@localhost";
        try {
            factory.setUri(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(declare, false, false, false, null);
            channel.basicPublish("", declare, null, sendString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Bean
    public static void addTrx(String trxString) {
        connectRabbitMQ(trxString, "android_addTrx");
    }

    @Bean
    public static void getTrx(String trxString) {
        connectRabbitMQ(trxString, "android_getTrx");
    }

    @Bean
    public static void buyVoucher(String trxString) {
        connectRabbitMQ(trxString, "android_buyVoucher");
    }

    @Bean
    public static void addNasabah(String trxString) {
        connectRabbitMQ(trxString, "android_addNasabah");
    }

    @Bean
    public static void loginNasabah(String trxString) {
        connectRabbitMQ(trxString, "android_loginNasabah");
    }

    @Bean
    public static void getAuth(String trxString) {
        connectRabbitMQ(trxString, "android_getAuth");
    }

    @Bean
    public static void getNasabah(String trxString) {
        connectRabbitMQ(trxString, "android_getNasabah");
    }

    @Bean
    public static void getXL() {
        connectRabbitMQ("getXL", "android_getXL");
    }

    @Bean
    public static void verifyJWT(String jwt) {
        connectRabbitMQ(jwt, "android_verifyJWT");
    }

    @Bean
    public static void getTelkomsel() {
        connectRabbitMQ("getTelkomsel", "android_getTelkomsel");
    }

}