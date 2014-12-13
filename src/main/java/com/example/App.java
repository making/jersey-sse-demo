package com.example;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Named;

@SpringBootApplication
public class App {
    @Named
    static class JerseyConfig extends ResourceConfig {
        public JerseyConfig() {
            this.packages("com.example");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}