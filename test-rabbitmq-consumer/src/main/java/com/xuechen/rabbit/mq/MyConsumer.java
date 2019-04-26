package com.xuechen.rabbit.mq;

import com.rabbitmq.client.Channel;
import com.xuechen.rabbit.config.RabbitMqReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(queues = {"queue_inform_sms"})
public class MyConsumer {
    private static Logger LOG= LoggerFactory.getLogger(RabbitMqReturn.class);
    @RabbitHandler
    public void processMessage(@Payload Map m, Channel chanel, Message message){
        LOG.info("receive message: "+(String)m.get("pageId"));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            chanel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            LOG.error("receive message error: "+e.getMessage());
        }

    }
}
