package com.msp.seckill.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(Object msg){
        log.info("发送了：" + msg.toString());
        rabbitTemplate.convertAndSend("queue", msg);
    }

}
