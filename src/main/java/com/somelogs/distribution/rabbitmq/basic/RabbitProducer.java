package com.somelogs.distribution.rabbitmq.basic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.somelogs.distribution.rabbitmq.ConnectionUtil;
import com.somelogs.distribution.rabbitmq.NameConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Producer
 *
 * @author LBG - 2019/4/11
 */
public class RabbitProducer {

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        //directExchange(connection);
        //fanoutExchange(connection);
        //topicExchange(connection);
        exchangeBind(connection);
    }

    /**
     * direct 类型的 Exchange：只有 RoutingKey 和 BindingKey 完全匹配才能正确路由消息
     */
    private static void directExchange(Connection connection) {
        Channel channel = null;
        try {
            // 创建通道
            channel = connection.createChannel();
            // 创建一个 type = "direct"、持久化的、非自动删除的交换器
            // exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments)
            channel.exchangeDeclare(NameConstant.EXCHANGE_NAME, "direct", true, false, null);
            channel.queueDeclare(NameConstant.QUEUE_NAME, true, false, false, null);
            // 将交换器与队列通过路由键绑定
            // 其实这里需要的是BindingKey, 只不过这里的 BindingKey 和 RoutingKey 是同一个东西
            channel.queueBind(NameConstant.QUEUE_NAME, NameConstant.EXCHANGE_NAME, NameConstant.ROUTING_KEY);

            String message = "direct Hello World " + new SimpleDateFormat("mm:ss").format(new Date());
            // 发送消息的时候，需要的是 RoutingKey
            channel.basicPublish(NameConstant.EXCHANGE_NAME, NameConstant.ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch(Exception e) {
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

    /**
     * fanout 类型的 Exchange：所有发送到该交换器的消息路由到所有与该交换器绑定的队列（无视 BindingKey）
     */
    private static void fanoutExchange(Connection connection) {
        String exchangeName = "fanout_exchange_demo";
        String queueName = "fanout_queue_demo";
        String bindingKey = "binding_key_fanout_demo";
        Channel channel = null;
        try {
            // 创建通道
            channel = connection.createChannel();
            // 创建交换器
            channel.exchangeDeclare(exchangeName, "fanout", true, false, null);
            // 创建队列
            channel.queueDeclare(queueName, true, false, false, null);
            // 将交换器与队列通过路由键绑定
            channel.queueBind(queueName, exchangeName, bindingKey);
            // 发送消息的时候，需要的是 RoutingKey
            // 为了测试 fanout 类型交换器，RoutingKey 和 BindingKey 设成不一致
            String message = "fanout Hello World " + new SimpleDateFormat("mm:ss").format(new Date());
            channel.basicPublish(exchangeName, NameConstant.ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch(Exception e) {
            throw new RuntimeException("exchange error:" + e);
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

    /**
     * topic 类型 Exchange：有如下约定
     * RoutingKey：点号“.” 分隔的字符串，每段字符串称为单词，如com.rabbitmq.client
     * BindingKey：也是点号“.” 分隔的字符串；
     *              还可以存在两种特殊字符串“*”, "#"
     *              "*" 匹配一个单词
     *              "#" 匹配多个单词（可以是零个）
     */
    private static void topicExchange(Connection connection) {
        String exchangeName = "topic_exchange_demo";
        String queueName = "topic_queue_demo";

        //String bindingKey = "*.*.client";
        //String routingKey = "com.rabbitmq.client"; // 匹配
        //String routingKey = "rabbitmq.client"; // 不匹配

        String bindingKey = "com.#";
        //String routingKey = "com.rabbit.client"; // 匹配
        String routingKey = "com"; // 匹配

        Channel channel = null;
        try {
            // 创建通道
            channel = connection.createChannel();
            // 创建交换器
            channel.exchangeDeclare(exchangeName, "topic", true, false, null);
            // 创建队列
            channel.queueDeclare(queueName, true, false, false, null);
            // 将交换器与队列通过路由键绑定
            channel.queueBind(queueName, exchangeName, bindingKey);
            // 发送消息的时候，需要的是 RoutingKey
            String message = "topic Hello World " + new SimpleDateFormat("mm:ss").format(new Date());
            channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch(Exception e) {
            throw new RuntimeException("exchange error:" + e);
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

    /**
     * 一般是交换器和队列绑定，还可以将交换器和交换器绑定
     */
    private static void exchangeBind(Connection connection) {
        String sourceExchangeName = "source_direct_exchange_demo";
        String destinationExchangeName = "destination_fanout_exchange_demo";
        String queueName = "exchange_queue_demo";
        // source 和 destination 的绑定键
        String xxBindingKey = "x_x_binding_key_demo";
        Channel channel = null;
        try {
            // 创建通道
            channel = connection.createChannel();
            // 创建交换器
            channel.exchangeDeclare(sourceExchangeName, "direct", true, false, null);
            channel.exchangeDeclare(destinationExchangeName, "fanout", true, false, null);
            channel.exchangeBind(destinationExchangeName, sourceExchangeName, xxBindingKey);
            // 创建队列
            channel.queueDeclare(queueName, true, false, false, null);
            // destination 交换器和队列绑定
            // 因为 destination 交换器类型声明为 fanout，是忽略绑定键的，所以这里RoutingKey设为""
            channel.queueBind(queueName, destinationExchangeName, "");
            // 发送消息的时候，需要的是 RoutingKey
            String message = "exchange Hello World " + new SimpleDateFormat("mm:ss").format(new Date());
            channel.basicPublish(sourceExchangeName, xxBindingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch(Exception e) {
            throw new RuntimeException("exchange error:" + e);
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
