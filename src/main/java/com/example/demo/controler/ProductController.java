package com.example.demo.controler;

import com.example.demo.model.Product;
import com.example.demo.serivce.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productService) {
        this.productServiceImpl = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productServiceImpl.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getAllProductById( @PathVariable int id) {
        return productServiceImpl.getProductById( id);
    }

    @PostMapping
    public Product createProduct( @RequestBody Product product) {
        return productServiceImpl.createProduct( product);
    }

    @PutMapping
    public String updateProduct( @RequestBody Product product) {
        if( productServiceImpl.updateProduct( product))
            return "{ \"result\": \"OK\" }";
        else
            return "{ \"result\": \"Not Found\" }";
    }


//    @DeleteMapping("/{id}")
//    public void deleteProduct( @PathVariable long id) {
//        productService.deleteProduct( id);
//    }

    @DeleteMapping("/{id}")
    public String deleteProduct( @PathVariable long id) {
        if( productServiceImpl.deleteProduct( id))
            return "{ \"result\": \"OK\" }";
        else
            return "{ \"result\": \"Not Found\" }";
    }
}
