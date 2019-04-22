package com.somelogs.distribution.rabbitmq.advanced;

import com.rabbitmq.client.*;
import com.somelogs.distribution.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Alternate Exchange, 备份交换器
 * 消息在发送的时候，不过不设置 mandatory，那么消息在没有被路由的情况下将会丢失。
 * 如果设置了 mandatory 参数，那么需要添加 ReturnListener 逻辑，生产者的代码将变得复杂。
 * 如果既不想生产者代码复杂，又不想消息丢失，那么就可以使用备份交换器。
 * 这样可以将未被路由的消息存在 RabbitMQ 中，需要的时候再去处理这些消息
 *
 * @author LBG - 2019/4/22
 */
public class AlternateExchange {

    private static final String NORMAL_EXCHANGE = "normalExchange";
    private static final String NORMAL_QUEUE = "normalQueue";
    private static final String ROUTING_KEY = "normalRoutingKey";

    private static final String AE_EXCHANGE = "aeExchange";
    private static final String UNROUTED_QUEUE = "unroutedQueue";

    public static void main(String[] args) throws InterruptedException {
        new Producer().publishMsg();
        TimeUnit.SECONDS.sleep(3);
        new Consumer().consumeMsg();
    }

    static class Producer {
        public void publishMsg() {
            try(Connection connection = ConnectionUtil.getConnection()) {
                Channel channel = connection.createChannel();
                // 创建备份交换器
                channel.exchangeDeclare(AE_EXCHANGE, BuiltinExchangeType.FANOUT.getType(), true, false, null);
                channel.queueDeclare(UNROUTED_QUEUE, true, false, false, null);
                channel.queueBind(UNROUTED_QUEUE, AE_EXCHANGE, "");
                // 创建 normalExchange
                Map<String, Object> args = new HashMap<>(1);
                args.put("alternate-exchange", AE_EXCHANGE);
                channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT.getType(), true, false, args);
                channel.queueDeclare(NORMAL_QUEUE, true, false, false, null);
                channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, ROUTING_KEY);
                // 发送消息, 把 Routing key 设置成一个匹配不到队列的值
                byte[] bytes = "AE message coming!".getBytes();
                channel.basicPublish(NORMAL_EXCHANGE, "non.existed", MessageProperties.PERSISTENT_TEXT_PLAIN, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer {
        public void consumeMsg() {
            try(Connection connection = ConnectionUtil.getConnection()) {
                final Channel channel = connection.createChannel();
                channel.basicQos(64);
                // 消费者订阅 UNROUTED_QUEUE
                channel.basicConsume(UNROUTED_QUEUE, false, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.out.println("message: " + new String(body));
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
