package com.somelogs.distribution.rabbitmq.advanced;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.somelogs.distribution.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * 生产者确认
 *
 * @author LBG - 2019/4/24
 */
public class ProducerConfirm {

    private static final String EXCHANGE_NAME = "tx_exchange";
    private static final String QUEUE_NAME = "tx_queue";
    private static final String ROUTING_KEY = "tx_routingKey";

    public static void main(String[] args) throws IOException {
        //transactionMode();
        //publisherConfirm();
        asynConfirm();
    }

    private static void transactionMode() throws IOException {
        Channel channel = null;
        try(Connection connection = ConnectionUtil.getConnection()) {
            channel = connection.createChannel();
            channel.txSelect(); // 将信道设置为信道模式
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, null);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            String message = "tx message " + new SimpleDateFormat("mm:ss");
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            int result = 1 / 0;
            channel.txCommit();
        } catch (IOException e) {
            e.printStackTrace();
            if (channel != null) {
                channel.txRollback();
            }
        }
    }

    /**
     * 同步确认
     */
    private static void publisherConfirm() {
        try(Connection connection = ConnectionUtil.getConnection()) {
            Channel channel = connection.createChannel();
            channel.confirmSelect(); // 将信道设置为 publisher confirm 模式
            String message = "tx message " + new SimpleDateFormat("mm:ss");
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            if (channel.waitForConfirms()) {
                System.out.println("消息发送成功");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量同步
     */
    private static void batchConfirm() {

    }

    /**
     * 异步确认
     */
    private static void asynConfirm() {
        try(Connection connection = ConnectionUtil.getConnection()) {
            final SortedSet<Long> unconfirmSet = new TreeSet<>();
            Channel channel = connection.createChannel();
            channel.confirmSelect();
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("ack no: " + deliveryTag);
                    if (multiple) {
                        unconfirmSet.headSet(deliveryTag -1).clear();
                    } else {
                        unconfirmSet.remove(deliveryTag);
                    }
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("nack no: " + deliveryTag);
                    if (multiple) {
                        unconfirmSet.headSet(deliveryTag -1).clear();
                    } else {
                        unconfirmSet.remove(deliveryTag);
                    }
                    // 这里需要有重发逻辑
                }
            });

            // 发送消息
            while (true) {
                long nextPublishSeqNo = channel.getNextPublishSeqNo();
                String message = "tx message " + new SimpleDateFormat("mm:ss");
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                unconfirmSet.add(nextPublishSeqNo);
                TimeUnit.SECONDS.sleep(2);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
