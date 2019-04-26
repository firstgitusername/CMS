package com.xuechen.test.rabbitmq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Connsumer01_headers_sms {
    //private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_HEADER_INFORM="exchange_header_inform";
    private static Logger LOG= LoggerFactory.getLogger(Consumer01_Routing_email.class);
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
           // channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            //交换机和对列绑定
            Map<String,Object> sms=new Hashtable<String,Object>();
            sms.put("x-match", "all");
            sms.put("inform_type0","sms");
            sms.put("inform_type1","email");
            //Map<String,Object> email=new Hashtable<String,Object>();
            //email.put("inform_type","email");
           // channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_HEADER_INFORM,"",email);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_HEADER_INFORM,"",sms);
            //channel.basicPublish(EXCHANGE_DIRECT_INFORM,"email",null,"send email message".getBytes());
            /*for (int i=0;i<5;i++){
                AMQP.BasicProperties.Builder basicProperties = new AMQP.BasicProperties().builder();
                Map<String,Object> param=new Hashtable<String,Object>();
                param.put("inform_type0","sms");
                param.put("inform_type1","email");
                basicProperties.headers(param);
                channel.basicPublish(EXCHANGE_HEADER_INFORM,"",basicProperties.build(),"send  email message all".getBytes());
            }*/

            DefaultConsumer consumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //super.handleDelivery(consumerTag, envelope, properties, body);
                    String s = new String(body, "utf-8");
                    LOG.info("receive message: "+s);
                }
            };
            channel.basicConsume(QUEUE_INFORM_SMS,true,consumer);
        }catch (Exception e){
             throw e;
        }finally {
           /* if(channel!=null){
                channel.close();
            }
            if(connection!=null) {
                connection.close();
            }*/
        }

    }
}
