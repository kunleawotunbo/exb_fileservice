/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Olakunle Awotunbo
 */
@SpringBootApplication
@EnableAutoConfiguration
public class FileServiceClientApplication {
     public static void main(final String[] args) {
        
        SpringApplication.run(FileServiceClientApplication.class, args);
    }
}
