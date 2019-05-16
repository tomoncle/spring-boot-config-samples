package com.tomoncle.app;


import com.tomoncle.config.springboot.EnableSpringBootConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.tomoncle.app"},
        scanBasePackageClasses = EnableSpringBootConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
