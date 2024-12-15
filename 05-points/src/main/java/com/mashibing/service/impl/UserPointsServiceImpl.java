package com.mashibing.service.impl;

import com.mashibing.mapper.UserPointsIdempotentMapper;
import com.mashibing.service.UserPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserPointsServiceImpl implements UserPointsService {

    @Resource
    private UserPointsIdempotentMapper userPointsIdempotentMapper;

    private final String ID_NAME = "spring_returned_message_correlation";


    @Override
    @Transactional
    public void consume(Message message) {
        // 获取生产者提供的CorrelationId要基于header去获取。
        String id = message.getMessageProperties().getHeader(ID_NAME);
        //1、查询幂等表是否存在当前消息标识
        int count = userPointsIdempotentMapper.findById(id);
        //2、如果存在，直接return结束
        if(count == 1){
            log.info("消息已经被消费！！！无需重复消费！");
            return;
        }
        //3、如果不存在，插入消息标识到幂等表
        userPointsIdempotentMapper.save(id);
        //4、执行消费逻辑
        // 预扣除用户积分
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("扣除用户积分成功！！");
    }
}