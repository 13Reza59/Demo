package com.example.demo.serivce;

import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public long getAllProductsCount() {
        return productRepository.count();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Other CRUD operations
}