package com.xuechen.manage_cms_client.mq;



import com.xuechen.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class ConsumerPostPage {
    @Autowired
    private PageService pageService;

    private static final Logger LOG= LoggerFactory.getLogger(ConsumerPostPage.class);


    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void post(@Payload Map<String,Object> message){
        //System.out.print(new String(message));
         String pageId=(String)message.get("pageId");
        LOG.info("receive cms post message: "+pageId);
        pageService.savePageToServerPath(pageId);

    }



}
