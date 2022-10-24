package com.jt.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jiangtao
 * @create 2022/9/20 19:37
 */
@Data
@Builder
public class Person implements Serializable {

    private String name;

    private int age;

}
