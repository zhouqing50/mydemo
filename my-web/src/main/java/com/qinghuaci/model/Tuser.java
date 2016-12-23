package com.qinghuaci.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Description:
 * Tuser: zhouq
 * Date: 2016/12/12
 */

@ToString
public class Tuser implements Serializable {

    @Getter
    @Setter
    Integer id;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    Integer age;

    @Getter
    @Setter
    String desc;
}
