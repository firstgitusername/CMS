package com.xuechen.rabbit.test.rabbitmq;

import com.xuechen.rabbit.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Rabbitmq_pro_sb_test {
    @Autowired
    private RabbitTemplate template;

    @Test
    public void sendSMS(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageId","35535");
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        template.convertAndSend(RabbitmqConfig.EXCHANGE_TOPIC_INFORM,"infore.sms",map,correlationId);
    }
}
