package com.run.nextjsapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.run.nextjsapi.dao")
public class NextjsAntdApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextjsAntdApiApplication.class, args);
    }

}
