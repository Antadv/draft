package com.somelogs.distribution.rabbitmq.advanced;

import com.rabbitmq.client.*;
import com.somelogs.distribution.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列
 *
 * @author LBG - 2019/4/23
 */
public class DelayQueue {
    private static final String NORMAL_EXCHANGE = "normalDelayExchange";
    private static final String NORMAL_QUEUE_PREFIX = "normalQueue_";

    private static final String DELAY_EXCHANGE_PREFIX = "delay_exchange_";
    private static final String DELAY_QUEUE_PREFIX = "delay_queue_";
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        // 创建死信队列执行一次就好
        //preCreateDelayQueue();

        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                new Consumer().consumeMsg();
            }
        });
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                new Producer().publishMsg();
            }
        });
    }

    /**
     * 提前将 dlx 和 死信队列创建好
     */
    private static void preCreateDelayQueue() {
        try(Connection connection = ConnectionUtil.getConnection()) {
            Channel channel = connection.createChannel();
            // 创建 normal exchange
            channel.exchangeDeclare(NORMAL_EXCHANGE, "direct", true, false, null);
            // 创建 normal queue，并分别设置队列消息的过期时间
            for (int i = 5; i <= 15; i+=5) {
                // 创建死信队列
                String delayExchangeName = DELAY_EXCHANGE_PREFIX + i;
                String delayQueueName = DELAY_QUEUE_PREFIX + i;
                channel.exchangeDeclare(delayExchangeName, "fanout", true, false, null);
                channel.queueDeclare(delayQueueName, true, false, false, null);
                channel.queueBind(delayQueueName, delayExchangeName, "");

                // 创建 normal queue, 并分别设置队列消息的过期时间及添加 DLX
                Map<String, Object> args = new HashMap<>(2);
                args.put("x-dead-letter-exchange", delayExchangeName);
                args.put("x-message-ttl", i * 1000);
                String normalQueueName = NORMAL_QUEUE_PREFIX + i;
                channel.queueDeclare(normalQueueName, true, false, false, args);
                channel.queueBind(normalQueueName, NORMAL_EXCHANGE, i + "s");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Producer {
        public void publishMsg() {
            try(Connection connection = ConnectionUtil.getConnection()) {
                Channel channel = connection.createChannel();
                for (int i = 5; i <= 15; i+=5) {
                    // 发送消息
                    byte[] bytes = ("delay message " + i).getBytes();
                    String routingKey = i + "s";
                    channel.basicPublish(NORMAL_EXCHANGE, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer {
        public void consumeMsg() {
            try (Connection connection = ConnectionUtil.getConnection()) {
                final Channel channel = connection.createChannel();
                channel.basicQos(64);
                DefaultConsumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        System.out.println("delay message: " + new String(body));
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                };
                for (int i = 5; i <= 15; i+=5) {
                    channel.basicConsume(DELAY_QUEUE_PREFIX + i, false, consumer);
                }
                while (true);
                //channel.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
}
