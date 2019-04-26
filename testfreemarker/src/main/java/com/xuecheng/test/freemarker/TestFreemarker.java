package com.xuecheng.test.freemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.xuecheng.test.freemarker"})//扫描本项目下的所有类
public class TestFreemarker {
    public static void main(String[] args) {
        SpringApplication.run(TestFreemarker.class);
    }
}
