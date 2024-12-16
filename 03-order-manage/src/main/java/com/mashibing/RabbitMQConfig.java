package com.mashibing;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order_exchange";
    public static final String ORDER_QUEUE = "order_queue";

    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    @Bean
    public Exchange orderExchange(){
        return ExchangeBuilder.fanoutExchange(ORDER_EXCHANGE).build();
    }

    @Bean
    public Queue orderQueue(){
        return QueueBuilder.durable(ORDER_QUEUE).deadLetterExchange(DEAD_EXCHANGE).build();
    }

    @Bean
    public Exchange deadExchange(){
        return ExchangeBuilder.fanoutExchange(DEAD_EXCHANGE).build();
    }

    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    @Bean
    public Binding orderBinding(Exchange orderExchange,Queue orderQueue){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("").noargs();
    }

    @Bean
    public Binding deadBinding(Exchange deadExchange,Queue deadQueue){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("").noargs();
    }

}