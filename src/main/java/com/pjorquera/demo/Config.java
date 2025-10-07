package com.pjorquera.demo;

import java.util.Scanner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
class Tools {

    @Tool(description = "Schedule an appointment for a given person")
    void scheduleAppointment(String person) {
        System.out.println("Scheduled an appointment for " + person);
    }

}

@Configuration
public class Config {
    
    private final ChatClient chatClient;

    public Config(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, Tools tools) {
        chatClient = chatClientBuilder
            .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
            .defaultTools(tools)
            .build();
    }

    @Bean
    @Profile("!test")
    ApplicationRunner applicationRunner() {
        return _ -> {
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.print("User: ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input)) {
                        break;
                    }
                    System.out.println();
                    System.out.print("AI: ");
                    chatClient.prompt()
                        .user(input)
                        .stream()
                        .content()
                        .doOnNext(System.out::print)
                        .blockLast();
                    System.out.println();
                }
            }
        };
    }

}
