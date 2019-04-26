package com.xuechen.rabbit.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMqConfirm implements RabbitTemplate.ConfirmCallback {
    private static Logger LOG= LoggerFactory.getLogger(RabbitMqConfirm.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            LOG.info("消息发送到exchange成功,id: {}", correlationData.getId());
        }else{
            LOG.error("消息发送到exchange失败,原因: {}", cause);
        }
    }
}
