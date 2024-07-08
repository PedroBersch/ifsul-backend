package br.com.example.trabalhobackend.service;


import br.com.example.trabalhobackend.model.Product;
import br.com.example.trabalhobackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public Product save(Product newProduct){
        return repository.save(newProduct);
    }
    public Product findById(Long id){
        Optional<Product> product = repository.findById(id);
        return product.orElseThrow(() -> new RuntimeException());
    }
    public List<Product> findAllProduct(){
        return repository.findAll();
    }
    public void delete(Long id){
        repository.deleteById(id);
    }
    public Product update(Long id, Product product){
        Product dbProduct = repository.getReferenceById(id);
        updateProduct(dbProduct,product);
        return  repository.save(dbProduct);

    }
    private void updateProduct(Product dbProduct,Product newProduct){
        dbProduct.setName(newProduct.getName());
        dbProduct.setPrice(newProduct.getPrice());
    }
    public Long countProduct(){
        return repository.count();
    }
}
