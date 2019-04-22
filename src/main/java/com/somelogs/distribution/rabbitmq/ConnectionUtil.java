package com.somelogs.distribution.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ Connection Util
 *
 * @author LBG - 2019/4/22
 */
public class ConnectionUtil {

    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672; // RabbitMQ default port
    private static final String USERNAME = "guest"; // RabbitMQ default username
    private static final String PASSWORD = "guest"; // RabbitMQ default password

    private ConnectionUtil() {}

    public static Connection getConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException("new connection error:" + e.getMessage());
        }
    }
}
