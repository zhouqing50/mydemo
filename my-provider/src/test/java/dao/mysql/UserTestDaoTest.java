package dao.mysql;

import com.qinghuaci.dao.mysql.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/13
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class UserTestDaoTest {
    @Resource
    private UserDao userDao;

    @Test
    public void save() throws Exception{
//        userDao.createUser(1, "a21");
    }

    @Test
    public void batchInsert() throws Exception{
        long startTime = System.currentTimeMillis();
        log.info("start time={}", startTime);
        for (int i = 0; i < 500000; i++) {
            userDao.createUser(i, "testName"+i, "湖南地区"+i);
        }
        long endTime = System.currentTimeMillis();
        log.info("end time={}, cost={}", endTime, endTime-startTime);
    }
}
