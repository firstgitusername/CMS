package com.xuechen.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.*;

@Configuration
public class RabbitmqConfig{
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_TOPIC_INFORM="exchange_topic_inform";
    public static final String ROUTINGKEY_EMAIL="infore.#.email.#";
    public static final String ROUTINGKEY_SMS="infore.#.sms.#";

    @Bean(EXCHANGE_TOPIC_INFORM)
    public Exchange EXCHANGE_TOPIC_INFORM(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM).durable(true).build();
    }

    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        Queue queue=new Queue(QUEUE_INFORM_EMAIL);
        return queue;
    }

    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS(){
        Queue queue=new Queue(QUEUE_INFORM_SMS);
        return queue;
    }
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(EXCHANGE_TOPIC_INFORM)Exchange exchange,
    @Qualifier(QUEUE_INFORM_SMS) Queue queue){
          return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
    }
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(EXCHANGE_TOPIC_INFORM)Exchange exchange,
                                            @Qualifier(QUEUE_INFORM_EMAIL) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
    }
    @Bean
    public MessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

}
