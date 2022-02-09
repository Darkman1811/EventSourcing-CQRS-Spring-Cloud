package com.guestu.eventsourcing_axon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EventsourcingAxonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsourcingAxonApplication.class, args);
    }

}
