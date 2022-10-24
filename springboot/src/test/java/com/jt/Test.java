package com.jt;

import com.jt.dao.SortedMapper;
import com.jt.entity.SortedModel;
import com.jt.service.RedisService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jiangtao
 * @create 2022/10/3 21:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private SortedMapper sortedMapper;

    @Autowired
    private RedisService redisService;

    @org.junit.Test
    public void getSorted(){
        SortedModel sortedModel = sortedMapper.selectById("6bc21a2a6133d04ce379ed5abb6fe821");
        System.out.println(sortedModel);
    }

    @org.junit.Test
    public void redisInsert() {
        redisService.redisInsert();
    }

    @org.junit.Test
    public void redisGet(){
        redisService.redisGet();
    }

    @org.junit.Test
    public void redisString(){
        redisService.redisString();
    }

    @org.junit.Test
    public void redisList(){
        redisService.redisList();
    }
}
