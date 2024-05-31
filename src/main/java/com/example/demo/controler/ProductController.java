package com.example.demo.controler;

import com.example.demo.model.Product;
import com.example.demo.serivce.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @GetMapping
//    public long getAllProductsCount() {
//        return productService.getAllProductsCount();
//    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Other CRUD endpoints
}
