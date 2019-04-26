package com.xuechen.rabbit.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    private static final String QUEUE="HELLOW WORD";

    public static void main(String[] args) throws IOException, TimeoutException {
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
            channel.basicPublish("",QUEUE,null,"Hellow Word".getBytes());
        }catch (Exception e){
             throw e;
        }finally {
            if(channel!=null){
                channel.close();
            }
            if(connection!=null) {
                connection.close();
            }
        }

    }
}
