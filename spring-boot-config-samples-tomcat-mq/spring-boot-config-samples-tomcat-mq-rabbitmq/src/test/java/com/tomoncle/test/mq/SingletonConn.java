package com.tomoncle.test.mq;

import com.rabbitmq.client.Connection;
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
