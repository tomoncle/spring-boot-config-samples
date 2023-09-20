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

package com.tomoncle.test.mq.simple.queue;

import com.rabbitmq.client.*;
import com.tomoncle.test.mq.simple.SingletonConn;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tomoncle
 */
public class QueueConsumer {

    private final static String QUEUE_NAME = "Q1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = SingletonConn.get().getFactory().newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 定义回调函数
        Consumer consumerHandler = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String msg = new String(body);
                System.out.println(msg);
            }
        };
        //consumer参数是消息接收之后的处理方法
        channel.basicConsume(QUEUE_NAME, true, consumerHandler);
    }

}
