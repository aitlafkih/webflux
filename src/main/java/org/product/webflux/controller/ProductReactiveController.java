package org.product.webflux.controller;


import org.product.webflux.dao.ProductRepository;
import org.product.webflux.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductReactiveController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value="/products")
    public Flux<Product> findAll(){
        return productRepository.findAll();
    }

    @GetMapping(value="/products/{id}")
    public Mono<Product> findById(@PathVariable String id){
        return productRepository.findById(id);
    }
}
