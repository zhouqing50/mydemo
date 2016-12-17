package com.qinghuaci.mq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/17
 */

@Slf4j
@Service
public class PublisherConfirmsT implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        if (ack) {
            log.info("消息成功消费，消息ID={}", correlationData.getId());
        } else {
            log.error("消息失败消费，消息ID={}", correlationData.getId());
        }
    }
}
