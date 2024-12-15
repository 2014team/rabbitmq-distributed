package com.mashibing.service;


import org.springframework.amqp.core.Message;

public interface UserPointsService {

    public void consume(Message message);
}
