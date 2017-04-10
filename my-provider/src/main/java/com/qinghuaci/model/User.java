package com.qinghuaci.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/12
 */
@Entity(value = "user", noClassnameStored = true)
@ToString
public class User implements Serializable {

    @Id
    @Getter
    @Setter
    ObjectId id;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    Integer age;

    @Getter
    @Setter
    String desc;

    @Getter
    @Setter
    List<Tuser> tusers;
}
