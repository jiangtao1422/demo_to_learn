package com.jt.service.impl;

import com.jt.entity.Person;
import com.jt.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiangtao
 * @create 2022/10/23 20:53
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void redisInsert() {
        Person person = Person.builder()
                .name("张三")
                .age(18)
                .build();
//        redisTemplate.opsForValue().set("user1", person);
        redisTemplate.opsForHash().put("user", "user1", person);
//        redisTemplate.opsForHash().put("user", "user2", "zhangsan");

    }

    @Override
    public void redisGet() {
//        Object user1 = redisTemplate.opsForValue().get("user1");
        Object user1 = redisTemplate.opsForHash().get("user", "user1");
//        Object user2 = redisTemplate.opsForHash().get("user", "user3");
        System.out.println(user1);
    }

    @Override
    public void redisString() {
//        String user1 = stringRedisTemplate.opsForValue().get("user1");
//        Object user1 = stringRedisTemplate.opsForHash().get("user", "user1");
//        stringRedisTemplate.opsForHash().put("user","user3","lisi");
        Object user1 = stringRedisTemplate.opsForHash().get("user", "user1");
        System.out.println(user1);
    }

    @Override
    public void redisList() {
        Person person = Person.builder()
                .name("张三")
                .age(17)
                .build();
//        Long user = redisTemplate.opsForList().leftPush("user2", person);
//        System.out.println(user);
//        List user1 = redisTemplate.opsForList().range("user2", 0, -1);
        List<String> user1 = stringRedisTemplate.opsForList().range("user2", 0, -1);
        user1.forEach(System.out::println);
    }


}
