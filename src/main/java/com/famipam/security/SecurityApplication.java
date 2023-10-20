package com.famipam.security;

import com.famipam.security.entity.Product;
import com.famipam.security.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner test(ProductRepository productRepository) {
        return args -> {
            Optional<Product> product = productRepository.findById(12L);

            String name = product.map(Product::getName).orElse("EMPTY");
            System.out.println("NAME = " + name);
        };
    }
}
