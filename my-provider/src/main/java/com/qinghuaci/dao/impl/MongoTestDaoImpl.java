package com.qinghuaci.dao.impl;

import com.google.common.base.Strings;
import com.qinghuaci.dao.MongoTestDao;
import com.qinghuaci.model.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/12
 */

@Component
public class MongoTestDaoImpl implements MongoTestDao {

    @Resource
    private Datastore datastore;

    @Override
    public void save(User user) {
        datastore.save(user);
    }

    @Override
    public User find(User user) {
        Query<User> query = datastore.createQuery(User.class);
        if (null != user.getId()){
            query.field("id").equal(user.getId());
        }
        if (null != user.getAge()){
            query.field("age").equal(user.getAge());
        }
        if (!Strings.isNullOrEmpty(user.getName())){
            query.field("name").equal(user.getName());
        }
        return query.get();
    }

    @Override
    public List<User> findAll(User user) {
        Query<User> query = datastore.createQuery(User.class);
        if (null != user.getId()){
            query.field("id").equal(user.getId());
        }
        if (null != user.getAge()){
            query.field("age").equal(user.getAge());
        }
        if (!Strings.isNullOrEmpty(user.getName())){
            query.field("name").equal(user.getName());
        }
        return query.asList();
    }

    @Override
    public List<User> searchText(User user) {
        Query<User> query = datastore.createQuery(User.class);
        if (!Strings.isNullOrEmpty(user.getName())){
            query.search(user.getName());
        }
        return query.asList();
    }

    @Override
    public List<User> findByRegex(String filed, String regex) {
        Query<User> query = datastore.createQuery(User.class);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.filter(filed, pattern);
        return query.asList();
    }


}
