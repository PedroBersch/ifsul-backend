package br.com.example.trabalhobackend.config;

import br.com.example.trabalhobackend.model.Product;
import br.com.example.trabalhobackend.repository.ProductRepository;
import br.com.example.trabalhobackend.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstantiationData implements CommandLineRunner {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;
    Faker faker = new Faker();

    @Override
    public void run(String... args) {

        productRepository.save(new Product(faker.commerce().productName(), faker.number().randomDouble(2, 100, 1000)));
        productRepository.save(new Product(faker.commerce().productName(), faker.number().randomDouble(2, 100, 1000)));
        productRepository.save(new Product(faker.commerce().productName(), faker.number().randomDouble(2, 100, 1000)));
    }


}
