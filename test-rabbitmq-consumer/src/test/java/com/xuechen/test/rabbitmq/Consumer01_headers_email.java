package com.xuechen.test.rabbitmq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consumer01_headers_email {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String EXCHANGE_HEADER_INFORM="exchange_header_inform";
    private static Logger LOG= LoggerFactory.getLogger(Consumer01_headers_email.class);
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
            //申明交换机
            channel.exchangeDeclare(EXCHANGE_HEADER_INFORM, BuiltinExchangeType.HEADERS);
            //参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            //交换机和对列绑定
            Map<String,Object> email=new Hashtable<String,Object>();
            email.put("inform_type","email");
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_HEADER_INFORM,"",email);
            //channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_HEADER_INFORM,"",sms);
            //channel.basicPublish(EXCHANGE_DIRECT_INFORM,"email",null,"send email message".getBytes());
            DefaultConsumer consumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //super.handleDelivery(consumerTag, envelope, properties, body);
                    String s = new String(body, "utf-8");
                    LOG.info("receive message: "+s);
                }
            };
            channel.basicConsume(QUEUE_INFORM_EMAIL,true,consumer);
        }catch (Exception e){
             throw e;
        }finally {

        }

    }
}
