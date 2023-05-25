package com.tomoncle.test.mq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tomoncle.test.mq.SingletonConn;

import java.nio.charset.StandardCharsets;

public class SendEmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";


    public static void main(String[] argv) throws Exception {
        for (int i = 0; i < 10; i++) {
            sendToAnonymousQueue();
//        sendToDesignatedQueue();
        }

    }


    /**
     * 发布消息到交换机，发送消息到匿名队列 "缺点：没有消费者，消息会丢失"
     * <p>
     * 创建一个主题类型的交换机，定义路由的key, 发布消息到交换机
     * <p>
     * 如果发布消息时，没有队列绑定到交换机上（没有和 交换机-routingKey 绑定的队列），消息会丢失
     */
    private static void sendToAnonymousQueue() throws Exception {
        ConnectionFactory factory = SingletonConn.get().getFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 创建一个topic类型的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            // 定义一个路由的key
            String routingKey = "anonymous.info";

            String message = "Hello World!";

            // 发布消息到指定交换机上，指定路由的key
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

//            Thread.sleep(100000);
        }
    }

    /**
     * 发布消息到交换机，发送消息到指定队列, "缺点：其实如果使用指定队列，就丢失了主题的灵活性"
     * <p>
     * 创建一个主题类型的交换机，定义路由的key, 定义一个队列，绑定交换机到指定的队列，发布消息到交换机
     * <p>
     * 交换机根据路由的key, 将消息路由到队列(和 交换机-routingKey 绑定的队列)
     */
    private static void sendToDesignatedQueue() throws Exception {
        final String QUEUE_NAME = "temp-topic-queue";
        ConnectionFactory factory = SingletonConn.get().getFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 创建一个topic类型的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            // 定义一个路由的key
            String routingKey = "anonymous.info";
            // 创建一个指定的队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 配置 交换机-key-指定的队列
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

            String message = "Hello World!";

            // 发布消息到指定交换机上，指定路由的key
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

//            Thread.sleep(100000);
        }
    }

}
