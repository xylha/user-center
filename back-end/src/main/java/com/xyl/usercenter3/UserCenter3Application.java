package com.xyl.usercenter3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// MyBatis-plus会扫描这个包中的内容，自动添加数据库的增删改查代码
@MapperScan("com.xyl.usercenter3.mapper")
public class UserCenter3Application {

    public static void main(String[] args) {
//        System.out.println("Hello World");
        SpringApplication.run(UserCenter3Application.class, args);
    }

}
