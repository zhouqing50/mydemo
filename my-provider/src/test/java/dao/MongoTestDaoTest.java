package dao;

import com.qinghuaci.dao.MongoTestDao;
import com.qinghuaci.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/13
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class MongoTestDaoTest {
    @Resource
    private MongoTestDao mongoTestDao;

    @Test
    public void save() throws Exception{
        User user = new User();
        user.setAge(121);
        user.setName("wtest ccc");
        user.setDesc("writing tutorials on w3cschool.cc");
        mongoTestDao.save(user);
    }

    @Test
    public void findByRegex(){
        String filed = "name";
        String regex = "^.*" + "w3cschool" + ".*$"; // as SQL:  like " '%" + personName + "%' "
        List<User> results = mongoTestDao.findByRegex(filed, regex);
        log.info("findByRegex filed={}, regex={}, result={}", filed, regex, results);
    }

    @Test
    public void searchText(){
        User user = new User();
        user.setName("w3cschool");
        List<User> results = mongoTestDao.searchText(user);
        log.info("searchText user={}, result={}", user, results);
    }

    @Test
    public void find() throws Exception{
        User user = new User();
        user.setAge(88);
        user.setName("zzhhh");
        User result = mongoTestDao.find(user);
        log.info("find user={}, result={}", user, result);
        log.info("time={}, date={}", result.getId().getTimestamp(), result.getId().getDate());
    }

    @Test
    public void findAll() throws Exception{
        User user = new User();
        user.setAge(88);
        List<User> results = mongoTestDao.findAll(user);
        log.info("findAll user={}, result={}", user, results);
    }

    @Test
    public void save10000(){
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("test10000");
            mongoTestDao.save(user);
        }
        //2918
        log.info("save10000 time={}", System.currentTimeMillis()-startTime);
    }

    @Test
    public void findAll10000(){
        long startTime = System.currentTimeMillis();
        User user = new User();
        user.setName("test10000");
        List<User> results = mongoTestDao.findAll(user);
        //434
        log.info("findAll10000 time={}", System.currentTimeMillis()-startTime);
    }
}
