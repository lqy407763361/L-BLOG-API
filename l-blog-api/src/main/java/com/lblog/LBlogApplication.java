package com.lblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LBlogApplication {
    public static void main(String[] args){
        ApplicationContext app = SpringApplication.run(LBlogApplication.class, args);
    }
}
