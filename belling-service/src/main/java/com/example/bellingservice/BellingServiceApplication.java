package com.example.bellingservice;

import com.example.bellingservice.entities.Bill;
import com.example.bellingservice.entities.ProductItem;
import com.example.bellingservice.feign.CustomerRestClient;
import com.example.bellingservice.feign.ProductItemRestClient;
import com.example.bellingservice.model.Customer;
import com.example.bellingservice.model.Product;
import com.example.bellingservice.repository.BillRepository;
import com.example.bellingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BellingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BellingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient){
        return args -> {

            Customer customer=customerRestClient.getCustomerById(1L);
            Bill bill1 = billRepository.save(new Bill(null, new Date(),null , customer.getId(),null));
            PagedModel<Product> productPagedModel =productItemRestClient.pageProducts();
            productPagedModel.forEach(p->{
                ProductItem productItem = new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+ new Random().nextInt(100));
                productItem.setBill(bill1);
                productItem.setProductID(p.getId());
                productItemRepository.save(productItem);
            });
        };
    }
}
