package com.jt.service.impl;

import com.jt.entity.Person;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangtao
 * @create 2022/9/15 21:12
 */
@Service
public class AuthService implements Serializable {

    public List<String> getCandidateUsers() {
        List<String> users = new ArrayList<>();
        users.add("小二");
        users.add("小三");
        users.add("小四");
        users.add("小五");
        return users;
    }

    public Person getAssign() {
//        List<String> users = new ArrayList<>();
//        users.add("测试人员1");
//        users.add("测试人员2");
        Person person = Person.builder().name("测试").age(18).build();
        return person;
    }
}
