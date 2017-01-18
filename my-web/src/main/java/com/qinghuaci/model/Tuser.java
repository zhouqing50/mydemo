package com.qinghuaci.model;

import lombok.*;

import java.io.Serializable;

/**
 * Description:
 * Tuser: zhouq
 * Date: 2016/12/12
 */

@Data
@NoArgsConstructor
@ToString
public class Tuser implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

    private String desc;
}
