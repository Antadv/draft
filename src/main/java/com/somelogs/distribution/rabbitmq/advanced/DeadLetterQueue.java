package com.somelogs.distribution.rabbitmq.advanced;

import com.rabbitmq.client.*;
import com.somelogs.distribution.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列
 *
 * @author LBG - 2019/4/22
 */
public class DeadLetterQueue {

    private static final String NORMAL_EXCHANGE = "normalDLXExchange";
    private static final String NORMAL_QUEUE = "normalDLXQueue";
    private static final String ROUTING_KEY = "normalRoutingKey";

    private static final String DLX = "dlxExchange";
    private static final String DLX_QUEUE = "dlxQueue";
    private static final int EXPIRE_TIME = 5 * 1000;

    public static void main(String[] args) throws InterruptedException {
        new Producer().publishMsg();
        // 等消息过期
        TimeUnit.SECONDS.sleep(10);
        new Consumer().consumeMsg(NORMAL_QUEUE);
        new Consumer().consumeMsg(DLX_QUEUE);
    }

    static class Producer {
        public void publishMsg() {
            try(Connection connection = ConnectionUtil.getConnection()) {
                Channel channel = connection.createChannel();
                // 创建死信交换器, 死信队列
                channel.exchangeDeclare(DLX, BuiltinExchangeType.FANOUT.getType(), true, false, null);
                channel.queueDeclare(DLX_QUEUE, true, false, false, null);
                channel.queueBind(DLX_QUEUE, DLX, "");
                // 创建 normalExchange
                channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT.getType(), true, false, null);
                Map<String, Object> args = new HashMap<>(2);
                args.put("x-message-ttl", EXPIRE_TIME); // 设置队列消息的过期时间
                args.put("x-dead-letter-exchange", DLX); // 为队列添加死信交换器
                channel.queueDeclare(NORMAL_QUEUE, true, false, false, args);
                channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, ROUTING_KEY);
                // 发送消息
                byte[] bytes = "dlx message coming!".getBytes();
                channel.basicPublish(NORMAL_EXCHANGE, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer {
        public void consumeMsg(final String queueName) {
            try(Connection connection = ConnectionUtil.getConnection()) {
                final Channel channel = connection.createChannel();
                channel.basicQos(64);
                // 消费者订阅 DLX_QUEUE
                channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.out.println(queueName + " dlx message: " + new String(body));
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                });

                TimeUnit.SECONDS.sleep(3);
                channel.close();
            } catch (IOException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
