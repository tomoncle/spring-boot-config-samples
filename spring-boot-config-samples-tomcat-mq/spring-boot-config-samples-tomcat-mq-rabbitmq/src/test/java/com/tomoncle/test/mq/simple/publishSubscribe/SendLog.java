/*
 * Copyright 2023 tomoncle
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

package com.tomoncle.test.mq.simple.publishSubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tomoncle.test.mq.simple.SingletonConn;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author tomoncle
 *
 *
 * 发布订阅模式：
 *
 * 生产者 -> 交换机 -> 队列1 -> 消费者1
 *                -> 队列2 -> 消费者2
 *                -> 队列n -> 消费者n
 *
 * 生产者: 将消息发送到交换机，不需要关心具体发送到哪些队列，这个由交换机处理
 * 交换机: 接收来自生产者的消息，另一方面，将它们推入队列。规则由交换器的类型(direct, topic, headers 和 fanout)定义.
 * 队列 : 存储消息，当创建一个消费者时，消费者会使用一个队列（可以是临时创建的，也可以是持久的）绑定到交换机，使用此队列用来接收来自交换机到消息
 * 消费者: 消费来自队列的消息
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
