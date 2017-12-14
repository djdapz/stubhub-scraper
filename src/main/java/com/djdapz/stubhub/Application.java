package com.djdapz.stubhub;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@SpringBootApplication
public class Application {
    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }
}