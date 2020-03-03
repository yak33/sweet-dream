package com.junya;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * sweet-dream启动类
 *
 * @author ZHANGCHAO
 */
@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.junya.dao")
public class DreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamApplication.class, args);
    }

}
