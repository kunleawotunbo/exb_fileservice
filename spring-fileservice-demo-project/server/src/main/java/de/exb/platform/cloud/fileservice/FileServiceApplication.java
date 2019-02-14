package de.exb.platform.cloud.fileservice;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class FileServiceApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC + 1"));
    }

    public static void main(final String[] args) {
        System.out.println("Starting application");
        SpringApplication.run(FileServiceApplication.class, args);
    }
}
