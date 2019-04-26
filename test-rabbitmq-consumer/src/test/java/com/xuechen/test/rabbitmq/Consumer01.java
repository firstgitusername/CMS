package com.xuechen.test.rabbitmq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer01 {
    private static final String QUEUE="HELLOW WORD";

    private static Logger LOG= LoggerFactory.getLogger(Consumer01.class);

    public static void main(String[] args) throws Exception{
        Connection connection=null;
        Channel channel=null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            connection = factory.newConnection();
            channel = connection.createChannel();
            //参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE, true, false, false, null);
            DefaultConsumer consumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //super.handleDelivery(consumerTag, envelope, properties, body);
                    String s = new String(body, "utf-8");
                    LOG.info("receive message: "+s);
                }
            };
            //监听对列
            channel.basicConsume(QUEUE,true,consumer);
        }catch (Exception e){
            throw e;
        }finally {
        }

    }
}
