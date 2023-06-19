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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tomoncle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRabbitMQApplication.class)// 指定spring-boot的启动类
public class ReceiveLogsTopic {
    private static Logger logger = LogManager.getLogger(ReceiveLogsTopic.class);
    private String queueName = "Q109";

    /**
     * @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
     */
    @Autowired
    private AmqpAdmin amqpAdmin;

    private void init() {
        logger.info("初始化...");

        String exchangeName = "topic_metrics";
        String routingKey = "anonymous.*";

        // 创建一个交换机
        amqpAdmin.declareExchange(new TopicExchange(exchangeName));
        logger.info("创建持久化交换机");

        // 创建一个持久化队列
        Queue queue = new Queue(queueName);
        amqpAdmin.declareQueue(queue);
        logger.info("创建持久化队列");

        // 将队列绑定到交换机
        amqpAdmin.declareBinding(BindingBuilder.bind(queue)
                .to(new TopicExchange(exchangeName))
                .with(routingKey));
        logger.info("绑定队列到交换机");
    }


    @Test
    public void receive() {
        init();
        // 队列必须要在接收之前初始化好
        Message receive = amqpTemplate.receive(queueName);
        while (null != receive) {
            System.out.println(new String(receive.getBody()));
            receive = amqpTemplate.receive(queueName);
            if (null == receive) {
                return;
            }
        }
    }
}
