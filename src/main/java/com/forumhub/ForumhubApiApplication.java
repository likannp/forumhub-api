package com.forumhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.forumhub"})
public class ForumhubApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumhubApiApplication.class, args);
    }
}
