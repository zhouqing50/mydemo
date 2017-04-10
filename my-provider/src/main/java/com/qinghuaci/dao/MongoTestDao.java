package com.qinghuaci.dao;

import com.qinghuaci.model.User;
import org.mongodb.morphia.Key;

import java.util.List;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/12
 */


public interface MongoTestDao {
    Key<User> save(User user);

    void addChildren(User user);

    void updateChildren(User user);

    User find(User user);

    List<User> findAll(User user);

    /**
     * 全文搜索，需要先创建全文索引
     * db.ttlsa_com.ensureIndex({"song":"text", "lyrics":"text"})
     * @param user
     * @return
     */
    List<User> searchText(User user);

    /**
     * 正则表达式搜索
     * @param filed 搜索的字段
     * @param regex 正则表达式
     * @return
     */
    List<User> findByRegex(String filed, String regex);
}
