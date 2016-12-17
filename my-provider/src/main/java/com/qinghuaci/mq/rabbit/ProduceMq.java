package com.qinghuaci.mq.rabbit;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.qinghuaci.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/17
 */
@Slf4j
@Service
public class ProduceMq {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(User user) {
        final Schema<User> schema = RuntimeSchema.getSchema(User.class);
        byte[] bytes = ProtobufIOUtil.toByteArray(user, schema, LinkedBuffer.allocate(256));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType("userTest");
        messageProperties.setHeader("key", "111");
        Message msg = null;
        try {
            msg = new Message(bytes, messageProperties);
            rabbitTemplate.send(msg);
            log.info("rabbit send success, msg={}", msg);
        } catch (AmqpException e) {
            log.warn("rabbit send failed, msg={}", msg);
        }
    }

}
