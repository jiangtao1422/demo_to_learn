package com.jt;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.jt.provider.service.impl")
public class ProviderApplication extends SpringBootServletInitializer
{

    public static void main(String[] args)
    {
        SpringApplication.run(ProviderApplication.class, args);
    }

}
