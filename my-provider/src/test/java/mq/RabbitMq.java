package mq;

import com.qinghuaci.model.User;
import com.qinghuaci.mq.rabbit.ConsumerMq;
import com.qinghuaci.mq.rabbit.ProduceMq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/17
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class RabbitMq {

    @Resource
    private ProduceMq produceMq;

    @Resource
    private ConsumerMq consumerMq;

    @Test
    public void send(){
        User user = new User();
        user.setName("aaaa");
        user.setAge(11);
        user.setDesc("测试rabbitmq22222");
        produceMq.send(user);
    }

}
