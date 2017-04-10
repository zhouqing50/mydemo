package com.qinghuaci.dao.mysql;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * Description:
 * User: zhouq
 * Date: 2017/2/16
 */
public interface UserDao {

    @Insert("INSERT INTO tuser(id,name,address,create_time,modify_time) VALUES(#{id},#{name},#{address},NOW(),NOW())")
    void createUser(@Param("id") Integer id, @Param("name") String name, @Param("address") String address);

}
