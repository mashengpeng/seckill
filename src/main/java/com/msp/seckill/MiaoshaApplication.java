package com.msp.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.msp.seckill.mapper")
public class MiaoshaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }

}
