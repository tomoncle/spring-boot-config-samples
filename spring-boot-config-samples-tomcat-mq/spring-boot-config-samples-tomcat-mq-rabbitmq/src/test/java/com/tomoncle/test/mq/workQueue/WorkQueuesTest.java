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

package com.tomoncle.test.mq.workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;
import com.tomoncle.test.mq.SingletonConn;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 工作队列模式：
 * <p>
 * 生产者 -> 队列 ->（消费者1、消费者2）
 * <p>
 * 一个生产者对应多个消费者
 * <p>
 * 简单队列和工作队列的区别就是，工作队列有多个消费者，仅此而已
 * <p>
 * https://www.rabbitmq.com/getstarted.html
 * https://www.rabbitmq.com/tutorials/tutorial-two-java.html
 * <p>
 * 工作队列，用于在多个工作线程之间分配耗时的任务。
 * <p>
 * 工作队列（又名：任务队列）背后的主要思想是避免立即执行资源密集型任务并必须等待其完成。
 * 相反，我们将任务安排在以后完成。我们将任务封装为消息并将其发送到队列。
 * 在后台运行的工作进程将弹出任务并最终执行作业。
 * 当您运行许多工作线程时，任务将在它们之间共享。
 */
public class WorkQueuesTest {
    private static final String TASK_QUEUE_NAME = "task_queue";

    @SneakyThrows
    @Test
    public void task() {
        try (
                Connection connection = SingletonConn.get().getFactory().newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            for (int i = 0; i < 100; i++) {
                String message = "hello world " + i;
                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes(StandardCharsets.UTF_8));
                System.out.println("[x] 消息发送成功. '" + message + "'");
            }

        }
    }

    @SneakyThrows
    @Test
    public void work1() {
        final Connection connection = SingletonConn.get().getFactory().newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        // 在worker处理并确认前一条消息之前，不要当前worker发送新消息
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] 收到消息 '" + message + "'");
            try {
                wait(600);
            } finally {
                System.out.println("[x] 处理结束，执行手动 ACK.");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

    @SneakyThrows
    @Test
    public void work2() {
        final Connection connection = SingletonConn.get().getFactory().newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] 收到消息 '" + message + "'");
            try {
                wait(1000);
            } finally {
                System.out.println("[x] 处理结束，执行手动 ACK.");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

    @SneakyThrows
    @Test
    public void work3() {
        final Connection connection = SingletonConn.get().getFactory().newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] 收到消息 '" + message + "'");
            try {
                wait(1300);
            } finally {
                System.out.println("[x] 处理结束，执行手动 ACK.");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
        Thread.sleep(Integer.MAX_VALUE);
    }


    private void wait(int s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    private void work() {
        final Connection connection = SingletonConn.get().getFactory().newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        // 在worker处理并确认前一条消息之前，不要当前worker发送新消息
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(Thread.currentThread().getName() + " : [x] 收到消息 '" + message + "'");
            try {
                wait(600);
            } finally {
                System.out.println(Thread.currentThread().getName() + " : [x] 处理结束，执行手动 ACK.");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }

    @Test
    public void MultipleWork() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(300);
        pool.setCorePoolSize(1);
        pool.setMaxPoolSize(2);
        pool.setQueueCapacity(10);
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        pool.initialize();

        for (int i = 0; i < 5; i++) {
            pool.execute(this::work);
        }
        wait(100000);
    }


}
