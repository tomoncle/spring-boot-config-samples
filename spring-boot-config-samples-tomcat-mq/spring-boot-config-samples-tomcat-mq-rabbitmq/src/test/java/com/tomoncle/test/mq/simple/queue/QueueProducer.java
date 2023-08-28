/*
 * Copyright 2018 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomoncle.test.mq.simple.queue;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.tomoncle.test.mq.simple.SingletonConn;
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
public class QueueProducer {


    private final static String QUEUE_NAME = "Q1";

    /**
     * 创建一个队列
     */
    @SneakyThrows
    @Test
    public void createQueue() {
        try (Connection connection = SingletonConn.get().getFactory().newConnection();
             Channel channel = connection.createChannel()) {
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

    /**
     * 删除一个队列
     */
    @SneakyThrows
    @Test
    public void dropQueue() {
        try (Connection connection = SingletonConn.get().getFactory().newConnection();
             Channel channel = connection.createChannel()) {
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

    /**
     * 自动ACK
     */
    @SneakyThrows
    @Test
    public void producer() {
        try (Connection connection = SingletonConn.get().getFactory().newConnection();
             Channel channel = connection.createChannel()) {

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

    /**
     * 手动ACK
     */
    @SneakyThrows
    @Test
    public void producer2() {
        try (Connection connection = SingletonConn.get().getFactory().newConnection();
             Channel channel = connection.createChannel()) {
            // 创建队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 开启发布确认
            channel.confirmSelect();
            // 开启确认监听
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    System.out.println("✅OK-ACK: " + deliveryTag + " 是否批量：" + multiple);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    System.out.println("❌NO-ACK: " + deliveryTag + " 是否批量：" + multiple);
                }
            });

            for (int i = 0; i < 10; i++) {
                String message = "hello world " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("[x] Sent '" + message + "'");
            }

            Thread.sleep(1000);

        }

    }
}