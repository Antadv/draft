package com.somelogs.distribution.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Producer
 *
 * @author LBG - 2019/4/11
 */
public class RabbitProducer {

    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routing_key_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672; // RabbitMQ default port

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 创建连接
        try (Connection connection = factory.newConnection()) {
            // 创建通道
            Channel channel = connection.createChannel();
            // 创建一个 type = "direct"、持久化的、非自动删除的交换器
            // exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments)
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 将交换器与队列通过路由键绑定
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            // 发送消息
            String message = "Hello World";
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            // 关闭资源
            channel.close();
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }

    }
}
