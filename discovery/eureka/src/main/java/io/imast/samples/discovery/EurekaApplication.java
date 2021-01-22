package io.imast.samples.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The Eureka Application
 * 
 * @author davitp
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    /**
     * The entry point for "Eureka Application"
     * 
     * @param args The arguments
     */
    public static void main(String[] args) {
        // create an application
        SpringApplication.run(EurekaApplication.class, args);
    }
}