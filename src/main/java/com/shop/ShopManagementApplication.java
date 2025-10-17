package com.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.entity.Role;
import com.shop.entity.User;
import com.shop.repository.UserRepository;

@SpringBootApplication
public class ShopManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("pyaepyae").isEmpty()) {
                User owner = User.builder()
                        .username("pyaepyae")
                        .password(passwordEncoder.encode("password123"))
                        .email("pyaepyae@shop.com")
                        .role(Role.OWNER)
                        .build();
                userRepository.save(owner);
                System.out.println("Default 'owner' user created.");
            }
        };
    }
}
