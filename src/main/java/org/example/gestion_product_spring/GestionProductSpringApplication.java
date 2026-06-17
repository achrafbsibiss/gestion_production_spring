package org.example.gestion_product_spring;

import org.example.gestion_product_spring.entities.Product;
import org.example.gestion_product_spring.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionProductSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionProductSpringApplication.class, args);
    }

    // Step 4: test the DAO layer
    @Bean
    public CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder().name("Computer").price(5400.0).quantity(11).build());
            productRepository.save(Product.builder().name("Printer").price(1200.0).quantity(8).build());
            productRepository.save(Product.builder().name("Smartphone").price(3200.0).quantity(20).build());
            productRepository.findAll().forEach(p -> System.out.println(p.toString()));
        };
    }
}
