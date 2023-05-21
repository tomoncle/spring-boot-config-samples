package com.tomoncle.app.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMessager {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void send(String message) {

        amqpTemplate.convertAndSend(message);

    }


}
