package com.forumhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.forumhub")
public class ForumhubApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumhubApiApplication.class, args);
    }
}
