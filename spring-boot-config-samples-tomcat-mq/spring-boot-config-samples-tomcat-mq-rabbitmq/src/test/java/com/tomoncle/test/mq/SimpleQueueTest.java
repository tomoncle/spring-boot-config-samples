package com.tomoncle.test.mq;


import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import org.junit.Test;


/**
 * 简单队列：
 * <p>
 * 生产者 -> 队列 -> 消费者
 * <p>
 * 一个生产者对应一个消费者
 * <p>
 * https://www.rabbitmq.com/getstarted.html
 * https://www.rabbitmq.com/tutorials/tutorial-one-java.html
 */
public class SimpleQueueTest {


    private static ConnectionFactory factory = new ConnectionFactory();
    private final static String QUEUE_NAME = "Q1";

    static {
        factory.setHost("10.16.0.4");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("adm");
        factory.setPassword("123456");
    }


    @SneakyThrows
    @Test
    public void createQueue() {
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            /*
             * @param queue      创建一个队列，有就忽略，没有就创建
             * @param durable    持久化队列
             * @param exclusive  独占队列，只能当前队列使用
             * @param autoDelete 自动删除
             * @param arguments  自定义参数
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        }
    }

    @SneakyThrows
    @Test
    public void dropQueue() {
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            /*
             * @param queue      创建一个队列，有就忽略，没有就创建
             * @param durable    持久化队列
             * @param exclusive  独占队列，只能当前队列使用
             * @param autoDelete 自动删除
             * @param arguments  自定义参数
             */
            AMQP.Queue.DeleteOk deleteOk = channel.queueDelete("queue-1");
            System.out.println(deleteOk.getMessageCount());
        }
    }

    @SneakyThrows
    @Test
    public void producer() {
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {

            /*
             * @param queue      创建一个队列，有就忽略，没有就创建
             * @param durable    持久化队列
             * @param exclusive  独占队列，只能当前队列使用
             * @param autoDelete 自动删除
             * @param arguments  自定义参数
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            for (int i = 0; i < 10; i++) {
                String message = "hello world " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("[x] Sent '" + message + "'");
            }

        }

    }

    @SneakyThrows
    @Test
    public void consumer() {
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            Consumer consumerHandler = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    String msg = new String(body);
                    System.out.println(msg);
                }
            };
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            //consumer参数是消息接收之后的处理方法
            channel.basicConsume(QUEUE_NAME, true, consumerHandler);
            Thread.sleep(1000 * 60);
        }
    }

}
