package br.com.example.trabalhobackend.repository;

import br.com.example.trabalhobackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    long count();
}
