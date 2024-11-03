package com.example.demo;

import com.example.demo.Repository.ProductRepository;
import com.example.demo.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration) {
        restConfiguration.exposeIdsFor(Product.class);
        return args -> {
            productRepository.save(new Product(null, "Ordinateur", 888, 12));
            productRepository.save(new Product(null, "imprimante", 999, 3));
            productRepository.save(new Product(null, "smartphone", 1000, 10));
            productRepository.findAll().forEach(p -> {
                System.out.println(p.getName());
            });
        };

    }
}
