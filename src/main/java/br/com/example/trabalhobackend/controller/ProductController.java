package br.com.example.trabalhobackend.controller;

import br.com.example.trabalhobackend.model.Product;
import br.com.example.trabalhobackend.model.ProductList;
import br.com.example.trabalhobackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product p = service.save(product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ProductList> findAllProduct(){
        List<Product> pList = service.findAllProduct();
        Long qttdProduct = service.countProduct();
        ProductList productList = new ProductList(qttdProduct, pList);
        return ResponseEntity.ok(productList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){
        Product p = service.findById(id);
        return new ResponseEntity<>(p,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteProduct(@PathVariable Long id){
        service.delete(id);
        Map<String,String> message = new HashMap<>();
        message.put("message","DELETED. Product with id: " + id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateProduct(@PathVariable Long id, @RequestBody Product product){
        service.update(id,product);
        Map<String,String> message = new HashMap<>();
        message.put("message","UPDATED. Product with id: " + id);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
