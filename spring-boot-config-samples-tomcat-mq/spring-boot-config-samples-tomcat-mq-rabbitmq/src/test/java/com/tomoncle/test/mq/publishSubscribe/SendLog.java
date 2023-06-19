package com.tomoncle.test.mq.publishSubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tomoncle.test.mq.SingletonConn;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author tomoncle
 */
public class SendLog {
    private static final String EXCHANGE_NAME = "logs";

    @Test
    public void send() throws Exception {
        try (Connection connection = SingletonConn.get().getFactory().newConnection();
             Channel channel = connection.createChannel()) {

            // 定义一个fanout类型的交换机，此类型的交换机会采用广播的形式发布到所有消费者
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String message = "info: Hello World!";

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
