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

package com.tomoncle.test.mq.simple;

import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

public class SingletonConn {

    private static SingletonConn singletonConn;

    private static ConnectionFactory factory;


    private SingletonConn() {
        factory = new ConnectionFactory();
        factory.setHost("10.16.0.4");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("adm");
        factory.setPassword("123456");
    }

    @SneakyThrows
    public ConnectionFactory getFactory() {
        return factory;
    }


    public static synchronized SingletonConn get() {
        if (null == singletonConn) {
            singletonConn = new SingletonConn();
        }
        return singletonConn;
    }

}
