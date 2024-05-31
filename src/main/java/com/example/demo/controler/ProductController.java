package com.example.demo.controler;

import com.example.demo.model.Product;
import com.example.demo.serivce.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

//    @GetMapping
//    public long getAllProductsCount() {
//        return productService.getAllProductsCount();
//    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getAllProductById(@PathVariable int id) {
        return productService.getProductById( id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct( product);
    }

    @PutMapping("/products")
    public HttpStatus updateProduct(@RequestBody Product product) {
        if(  productService.updateProduct( product))
            return HttpStatus.OK;
        else
            return HttpStatus.NOT_FOUND;
    }


    @DeleteMapping("/products/{id}")
    public HttpStatus deleteProduct(@PathVariable long id) {
        if( productService.deleteProduct( id))
            return HttpStatus.OK;
        else
            return HttpStatus.NOT_FOUND;
    }
}
