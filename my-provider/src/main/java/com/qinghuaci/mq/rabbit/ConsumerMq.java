package com.qinghuaci.mq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/17
 */
@Slf4j
@Service
public class ConsumerMq implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        log.info("收到消息，msg={}", msg);
    }
}
