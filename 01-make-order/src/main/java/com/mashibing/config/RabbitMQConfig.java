package com.mashibing.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 下单服务的交换机
    public static final String PLACE_ORDER_EXCHANGE = "place_order_exchange";
    // 三个服务的Queue
    public static final String COUPON_QUEUE = "coupon_queue";
    public static final String USER_POINTS_QUEUE = "user_points_queue";
    public static final String BUSINESS_QUEUE = "business_queue";


    @Bean
    public Exchange placeOrderExchange(){
        return ExchangeBuilder.fanoutExchange(PLACE_ORDER_EXCHANGE).build();
    }

    @Bean
    public Queue couponQueue(){
        return QueueBuilder.durable(COUPON_QUEUE).build();
    }
    @Bean
    public Queue userPointsQueue(){
        return QueueBuilder.durable(USER_POINTS_QUEUE).build();
    }
    @Bean
    public Queue businessQueue(){
        return QueueBuilder.durable(BUSINESS_QUEUE).build();
    }

    @Bean
    public Binding couponBinding(Exchange placeOrderExchange,Queue couponQueue){
        return BindingBuilder.bind(couponQueue).to(placeOrderExchange).with("").noargs();
    }
    @Bean
    public Binding userPointsBinding(Exchange placeOrderExchange,Queue userPointsQueue){
        return BindingBuilder.bind(userPointsQueue).to(placeOrderExchange).with("").noargs();
    }
    @Bean
    public Binding businessBinding(Exchange placeOrderExchange,Queue businessQueue){
        return BindingBuilder.bind(businessQueue).to(placeOrderExchange).with("").noargs();
    }



}