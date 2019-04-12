package com.somelogs.distribution.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Consumer
 *
 * @author LBG - 2019/4/11
 */
public class RabbitConsumer {

    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRES = "127.0.0.1";
    private static final int PORT = 5672;

    public static void main(String[] args) {
        Address[] addresses = new Address[]{new Address(IP_ADDRES, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 创建连接
        try (Connection connection = factory.newConnection(addresses)) {
            // 创建信道
            final Channel channel = connection.createChannel();
            // 设置客户端最多接受未被ack消息的个数
            channel.basicQos(64);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("recv msg：" + new String(body));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            channel.basicConsume(QUEUE_NAME, consumer);

            // 等待回调函数执行完毕之后，关闭资源
            TimeUnit.SECONDS.sleep(5);
            channel.close();
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
