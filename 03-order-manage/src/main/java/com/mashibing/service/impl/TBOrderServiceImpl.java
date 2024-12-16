package com.mashibing.service.impl;

import com.mashibing.RabbitMQConfig;
import com.mashibing.mapper.TBOrderMapper;
import com.mashibing.service.TBOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;



@Service
@Slf4j
public class TBOrderServiceImpl implements TBOrderService {

    @Resource
    private TBOrderMapper orderMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void save() {
        // 生成主键ID
        String id = UUID.randomUUID().toString();
        // 创建订单
        orderMapper.save(id);
        // 订单构建成功~
        // 发送消息到RabbitMQ的死信队列
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, "", id, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息的生存时间为15s，当然，也可以在构建队列时，指定队列的生存时间。
                message.getMessageProperties().setExpiration("15000");
                return message;
            }
        });
    }

    @Override
    @Transactional
    public void delayCancelOrder(String id) {
        //1、基于id查询订单信息。 for update
        int orderState = orderMapper.findOrderStateByIdForUpdate(id);
        //2、判断订单状态
        if(orderState != 0){
            log.info("订单已经支付！！");
            return;
        }
        //3、修改订单状态
        log.info("订单未支付，修改订单状态为已取消");
        orderMapper.updateOrderStateById(-1,id);
    }

}