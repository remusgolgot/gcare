package com.gcare;

import com.gcare.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@ComponentScan(basePackages = "com/gcare")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
