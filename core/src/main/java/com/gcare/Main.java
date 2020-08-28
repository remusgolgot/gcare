package com.gcare;

import com.gcare.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@ComponentScan(basePackages = "com/gcare")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    public static void main(String[] args) {
//        String tl = "C:\\Users\\Remus\\Temp\\Gcare\\documents\\1\\1.jpg";
//        Path targetLocation = Paths.get(tl);
//        System.out.println(targetLocation.toFile().getParentFile().getAbsolutePath());
//        File folderCC = new File(targetLocation.toFile().getParentFile().getAbsolutePath());
//        System.out.println(folderCC.exists());
//        folderCC.mkdir();
//        System.out.println(folderCC.exists());
//        System.out.println(folderCC.getAbsolutePath());
//    }
}
