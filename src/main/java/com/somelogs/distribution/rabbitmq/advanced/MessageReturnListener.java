package com.somelogs.distribution.rabbitmq.advanced;

import com.rabbitmq.client.*;
import com.somelogs.distribution.rabbitmq.ConnectionUtil;
import com.somelogs.distribution.rabbitmq.NameConstant;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 介绍发送消息的两个参数：mandatory，immediate
 * @see com.rabbitmq.client.Channel#basicPublish(String, String, boolean, boolean, AMQP.BasicProperties, byte[])
 *
 * mandatory
 *  true：匹配不到队列时，RabbitMQ 调用 Basic.Return 命令将消息返回给生产者
 *  false：匹配不到队列时，RabbitMQ 直接丢弃消息
 *
 * immediate（RabbitMQ 3.0 以后不推荐使用）
 *  true：当匹配到的队列上不存在任何消费者，消息不会存入队列。
 *        当与路由键匹配的所有队列都没有消费者时，消息会通过 Basic.Return 返回给生产者
 *
 *
 * @author LBG - 2019/4/22
 */
public class MessageReturnListener {

    public static void main(String[] args) {
        mandatory();
    }

    private static void mandatory() {
        new Producer().publishMsg();
    }

    static class Producer {
        public void publishMsg() {
            Channel channel = null;
            Connection connection = null;
            try {
                connection = ConnectionUtil.getConnection();
                channel = connection.createChannel();
                channel.exchangeDeclare(NameConstant.EXCHANGE_NAME, "direct", true, false, null);
                channel.queueDeclare(NameConstant.QUEUE_NAME, true, false, false, null);
                channel.queueBind(NameConstant.QUEUE_NAME, NameConstant.EXCHANGE_NAME, NameConstant.ROUTING_KEY);
                String message = "mandatory Hello World " + new SimpleDateFormat("mm:ss").format(new Date());
                // 这里故意把 Routing key 设置成一个匹配不到队列的值
                // mandatory，true：交换器根据自身类型和路由键找不到一个匹配的队列是，RabbitMQ 会调用Basic.Return命令将消息返回给生产者
                //            false：消息直接被丢弃
                channel.basicPublish(NameConstant.EXCHANGE_NAME, "non.existed", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                // 设置回调
                channel.addReturnListener(new ReturnListener() {
                    @Override
                    public void handleReturn(int replyCode, String replyText,
                                             String exchange, String routingKey,
                                             AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.out.println("Basic Return 返回的结果是：" + new String(body));
                    }
                });
                TimeUnit.SECONDS.sleep(5);
            }catch(Exception e) {
                throw new RuntimeException("exchange error:" + e.getMessage());
            } finally {
                try {
                    // 关闭资源
                    if (channel != null) {
                        channel.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch(Exception e) {
                    System.out.println("close resource exception:" + e.getMessage());
                }
            }

        }
    }
}
