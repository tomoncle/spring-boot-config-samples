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

package com.tomoncle.test.mq.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author tomoncle
 */
@SpringBootApplication
public class TestRabbitMQApplication {
    private static Logger logger = LogManager.getLogger(TestRabbitMQApplication.class);

    public static void main(String[] args) {
        ApplicationContext application = SpringApplication.run(TestRabbitMQApplication.class, args);
        if (logger.isDebugEnabled()) {
            String[] beanDefinitionNames = application.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                logger.debug(beanName);
            }
        }
    }

//    /**
//     * 初始化 队列和交换机
//     *
//     * 1. 消费者强依赖，如果没有队列，消费者无法消费，会运行失败
//     * 2. 生产者弱依赖，如果没有，则生产的消息会丢失，不会运行失败
//     */
//    @Bean
//    public AmqpAdmin amqpAdmin(CachingConnectionFactory cachingConnectionFactory) {
//        logger.info("initial amqpAdmin");
//        AmqpAdmin amqpAdmin = new RabbitAdmin(cachingConnectionFactory);
//
//        // 创建一个交换机
//        amqpAdmin.declareExchange(new TopicExchange("topic_metrics"));
//        logger.info("创建持久化交换机");
//
//        // 创建一个持久化队列
//        Queue queue = new Queue("Q109");
//        amqpAdmin.declareQueue(queue);
//        logger.info("创建持久化队列");
//
//        // 将队列绑定到交换机
//        amqpAdmin.declareBinding(BindingBuilder.bind(queue)
//                .to(new TopicExchange("topic_metrics"))
//                .with("anonymous.error"));
//        logger.info("绑定队列到交换机");
//
//        return amqpAdmin;
//    }
}
