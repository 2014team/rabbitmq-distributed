package com.mashibing.controller;

import com.mashibing.client.*;
import com.mashibing.config.RabbitMQConfig;
import com.mashibing.util.GlobalCache;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class MakeOrderController {

    @Autowired
    private StockClient itemStockClient;
    @Autowired
    private OrderManageClient orderManageClient;
    @Autowired
    private CouponClient couponClient;
    @Autowired
    private PointsClient pointsClient;
    @Autowired
    private BusinessClient businessClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 模拟用户下单操作
     * @return
     */
    @GetMapping("/po")
    public String po(){
        long start = System.currentTimeMillis();
        //1、调用库存服务扣除商品库存
        itemStockClient.decr();
        //2、调用订单服务，创建订单
        orderManageClient.create();
        //3、调用优惠券服务，预扣除使用的优惠券
        couponClient.coupon();
        //4、调用用户积分服务，预扣除用户使用的积分
        pointsClient.up();
        //5、调用商家服务，通知商家用户已下单
        businessClient.notifyBusiness();

        long end = System.currentTimeMillis();
        System.out.println("==========>"+(end - start));
        return "place order is ok!";
    }

    @GetMapping("/rabbitmq")
    public String rabbitmq(){
        long start = System.currentTimeMillis();
        //1、调用库存服务扣除商品库存
        itemStockClient.decr();
        //2、调用订单服务，创建订单
        orderManageClient.create();
//        //3、调用优惠券服务，预扣除使用的优惠券
//        couponClient.coupon();
//        //4、调用用户积分服务，预扣除用户使用的积分
//        pointsClient.up();
//        //5、调用商家服务，通知商家用户已下单
//        businessClient.notifyBusiness();

        String userAndOrderInfo = "用户信息&订单信息&优惠券信息等等…………";

        // 声明当前消息的id标识
        String id = UUID.randomUUID().toString();
        // 封装消息的全部信息
        Map map = new HashMap<>();
        map.put("id",id);
        map.put("message",userAndOrderInfo);
        map.put("exchange",RabbitMQConfig.PLACE_ORDER_EXCHANGE);
        map.put("routingKey","");
        map.put("sendTime",new Date());
        // 将id标识和消息存储到全局缓存中
        GlobalCache.set(id,map);

        // 将同步方式修改为基于RabbitMQ的异步方式
       // rabbitTemplate.convertAndSend(RabbitMQConfig.PLACE_ORDER_EXCHANGE,"",userAndOrderInfo);
        rabbitTemplate.convertAndSend(RabbitMQConfig.PLACE_ORDER_EXCHANGE,"",userAndOrderInfo,new CorrelationData(id));


        long end = System.currentTimeMillis();
        System.out.println("==========>"+(end - start));
        return "place order is ok!";
    }




}