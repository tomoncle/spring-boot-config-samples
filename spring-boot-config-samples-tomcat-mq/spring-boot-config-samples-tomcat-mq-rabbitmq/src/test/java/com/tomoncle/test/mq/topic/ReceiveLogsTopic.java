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

package com.tomoncle.test.mq.topic;

import com.rabbitmq.client.*;
import com.tomoncle.test.mq.SingletonConn;

import java.nio.charset.StandardCharsets;

public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = SingletonConn.get().getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建一个topic类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 生成一个随机的 队列名称
        String queueName = channel.queueDeclare().getQueue();
        // 定义一个路由的key, anonymous 开头的key都路由到当前队列
        String bindingKey = "anonymous.*";
        // 绑定 交换机-key-队列
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
