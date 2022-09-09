package com.learning.readinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class ReadingListApplication {

    public static void main(String[] args) {

        SpringApplication.run(ReadingListApplication.class, args);
    }

}