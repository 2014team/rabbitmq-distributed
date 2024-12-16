package com.mashibing.listener;

import com.mashibing.RabbitMQConfig;
import com.mashibing.service.TBOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DelayMessageListener {

    @Autowired
    private TBOrderService orderService;

    @RabbitListener(queues = RabbitMQConfig.DEAD_QUEUE)
    public void consume(String id, Channel channel, Message message) throws IOException {
        //1、 调用Service实现订单状态的处理
        orderService.delayCancelOrder(id);

        //2、 ack的干活~
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}