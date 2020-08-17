package com.gcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
@ComponentScan(basePackages = "com/gcare")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
