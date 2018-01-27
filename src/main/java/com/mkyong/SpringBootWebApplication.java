package com.mkyong;

import com.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.mapper")
@ComponentScan("com")
public class SpringBootWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootWebApplication.class, args);
        UserMapper userMapper = context.getBean(UserMapper.class);

        System.out.println(userMapper.findAll(0, 10).size());
        userMapper.findAll(1, 10).forEach(user -> {
            System.out.println(user);
        });
        System.out.println("count user:" + userMapper.countUser());
    }

}