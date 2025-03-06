package com.lifeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        System.out.println("当前工作目录: " + System.getProperty("user.dir"));
        SpringApplication.run(LibraryApplication.class, args);
    }

}
