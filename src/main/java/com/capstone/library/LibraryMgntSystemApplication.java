package com.capstone.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibraryMgntSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryMgntSystemApplication.class, args);
    }

}
