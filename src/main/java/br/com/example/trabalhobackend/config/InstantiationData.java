package br.com.example.trabalhobackend.config;

import br.com.example.trabalhobackend.model.Product;
import br.com.example.trabalhobackend.model.Role;
import br.com.example.trabalhobackend.model.User;
import br.com.example.trabalhobackend.repository.ProductRepository;
import br.com.example.trabalhobackend.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InstantiationData implements CommandLineRunner {
    final ProductRepository productRepository;
    final UserRepository userRepository;
    Faker faker = new Faker();

    @Override
    public void run(String... args) {

        String adminPassword = new BCryptPasswordEncoder().encode("admin");
        String userPassword = new BCryptPasswordEncoder().encode("user");
        userRepository.save(new User("admin",adminPassword, Role.ADMIN));
        userRepository.save(new User("user",userPassword, Role.USER));

        productRepository.save(new Product(faker.commerce().productName(), faker.number().randomDouble(2, 100, 1000)));
        productRepository.save(new Product(faker.commerce().productName(), faker.number().randomDouble(2, 100, 1000)));
        productRepository.save(new Product(faker.commerce().productName(), faker.number().randomDouble(2, 100, 1000)));
    }


}
