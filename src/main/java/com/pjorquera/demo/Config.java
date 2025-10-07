package com.pjorquera.demo;

import java.util.Scanner;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    
    @Bean
    ApplicationRunner applicationRunner() {
        return _ -> {
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.print("User: ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input)) {
                        break;
                    }
                }
            }
        };
    }

}
