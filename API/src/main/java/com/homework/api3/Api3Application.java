package com.homework.api3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableWebSocket
@MapperScan(basePackages = "com.homework.api3.mapper")
@EnableAsync
public class  Api3Application {

    public static void main(String[] args) {
        SpringApplication.run(Api3Application.class, args);
    }

}
