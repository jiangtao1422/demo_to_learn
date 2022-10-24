package com.jt.consumer.controller;

import com.jt.provider.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author: jiangtao
 * @date: 2021/9/26 10:22
 */
@RestController
public class MainController {

    @DubboReference(group = "demo-provider", version = "1.0.0", check = false)
    private HelloService helloService;

    @GetMapping("/")
    public String index() {
        return helloService.sayHello("consumer");
    }
}
