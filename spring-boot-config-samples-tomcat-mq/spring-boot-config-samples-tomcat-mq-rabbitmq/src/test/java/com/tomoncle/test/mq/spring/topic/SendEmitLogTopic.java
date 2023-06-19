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

package com.tomoncle.test.mq.spring.topic;

import com.tomoncle.test.mq.spring.TestRabbitMQApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tomoncle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRabbitMQApplication.class)// 指定spring-boot的启动类
public class SendEmitLogTopic {

    /**
     * @see org.springframework.amqp.rabbit.core.RabbitTemplate#send(String, String, Message)
     * @see org.springframework.amqp.rabbit.core.RabbitTemplate#receive()
     * @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 默认发送的地址，和 spring.rabbitmq.template 配置相关，源码如下：
     *
     * @see org.springframework.boot.autoconfigure.amqp.RabbitProperties.Template
     * <p>
     * spring.rabbitmq.template.exchange=topic_metrics
     * spring.rabbitmq.template.routing-key=anonymous.*
     */
    @Test
    public void send() {
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("hello world! " + i);
        }
    }

    /**
     * 使用指定的exchange、routingKey发送匿名队列消息
     */
    @Test
    public void sendToAnonymousQueue() {
        String exchange = "topic_metrics";
        String routingKey = "anonymous.waring";
        String message = "hello world ! this is anonymous topic message.";
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }


    @Test
    public void sendToAnonymousQueue2() {
        String exchange = "topic_metrics";
        String routingKey = "anonymous.error";
        for (int i = 0; i < 10; i++) {
            String message = "hello world ! this is anonymous topic message." + i;
            amqpTemplate.convertAndSend(exchange, routingKey, message);
        }

    }
}
