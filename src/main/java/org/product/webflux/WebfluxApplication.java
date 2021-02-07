package org.product.webflux;

import org.product.webflux.dao.ProductRepository;
import org.product.webflux.dao.TransactionRepository;
import org.product.webflux.model.Product;
import org.product.webflux.model.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository, TransactionRepository transactionRepository){
        return args->{
            productRepository.deleteAll().subscribe(null,null, ()->{
            Stream.of("Apple","Samsung","Huawei","Oppo").forEach(p ->{
                productRepository.save(new Product(p,p,200+Math.random()*800)).subscribe(prod -> {
                    System.out.println(prod.toString());
                });
            });

            transactionRepository.deleteAll().subscribe(null,null,()->{
                    Stream.of("Apple","Samsung","Huawei","Oppo").forEach(p ->{
                        productRepository.findById(p).subscribe(prod -> {
                            for(int i =0; i<10;i++){
                                Transaction transaction = new Transaction();
                                transaction.setInstant(Instant.now());
                                transaction.setProduct(prod);
                                transaction.setQuantity((int) (10+Math.random()*8));
                                transactionRepository.save(transaction).subscribe(t->{
                                    System.out.println(t.toString());
                                });
                            }
                        });
                    });
                });

            });
        };
    }

}
