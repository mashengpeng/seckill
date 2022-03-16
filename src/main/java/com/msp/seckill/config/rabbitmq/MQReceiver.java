package com.msp.seckill.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {



    @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接受到了：" + msg.toString());
    }
}
