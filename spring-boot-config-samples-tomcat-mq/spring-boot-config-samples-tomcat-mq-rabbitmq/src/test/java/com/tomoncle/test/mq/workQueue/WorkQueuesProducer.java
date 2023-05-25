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
import com.rabbitmq.client.MessageProperties;
import com.tomoncle.test.mq.SingletonConn;
import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

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
 *
 * @author tomoncle
 */
public class WorkQueuesProducer {

    private static final String TASK_QUEUE_NAME = "task_queue";

    @SneakyThrows
    @Test
    public void task() {
        try (Connection connection = SingletonConn.get().getFactory().newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            for (int i = 0; i < 100; i++) {
                String message = "hello world " + i;
                //  MessageProperties.PERSISTENT_TEXT_PLAIN 表示持久化磁盘
                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes(StandardCharsets.UTF_8));

                System.out.println("[x] 消息发送成功. '" + message + "'");
            }

        }
    }

}
