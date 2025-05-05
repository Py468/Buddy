package net.enjoy.springboot.registrationlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistrationLoginApplication {

    public static void main(String[] args) {
        System.out.println("STARTING APPLICATION...");
        SpringApplication.run(RegistrationLoginApplication.class, args);
    }
}
