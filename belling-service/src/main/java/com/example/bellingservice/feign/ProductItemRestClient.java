package com.example.bellingservice.feign;

import com.example.bellingservice.model.Product;
import jakarta.ws.rs.QueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductItemRestClient {
    @GetMapping(path = "/products")
    PagedModel<Product>  pageProducts();
    @GetMapping(path = "/products/{id}")
    Product getProductByID(@PathVariable("id")Long id);
}
