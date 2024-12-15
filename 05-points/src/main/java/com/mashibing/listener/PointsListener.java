package com.mashibing.listener;

import com.mashibing.config.RabbitMQConfig;
import com.mashibing.service.UserPointsService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PointsListener {

    @Autowired
    private UserPointsService userPointsService;


    @RabbitListener(queues = {RabbitMQConfig.USER_POINTS_QUEUE})
    public void consume(String msg, Channel channel, Message message) throws Exception {
        // 预扣除优惠券
//        Thread.sleep(400);
//        System.out.println("优惠券预扣除成功！" + msg);

        userPointsService.consume(message);

        // 手动ACK
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

    }

}