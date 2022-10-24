package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangtao
 * @create 2022/9/9 23:38
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "测试成功！";
    }
}
