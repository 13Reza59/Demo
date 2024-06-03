package com.example.demo.controler;

import com.example.demo.model.Product;
import com.example.demo.serivce.ProductService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
//    @PreAuthorize( "hasRole('USER') or hasRole('ADMIN')")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Product getAllProductById( @PathVariable(value = "id") Long id) {
        return productService.getProductById( id);
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Product createProduct( @RequestBody Product product) {
        return productService.createProduct( product);
    }

    @PostMapping("/update")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String updateProduct( @RequestBody Product product) {
        JSONObject output = new JSONObject();

        if( productService.updateProduct( product))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }

    @PostMapping("/delete")
//    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct( @RequestBody Product product) {
        JSONObject output = new JSONObject();

        if( productService.deleteProduct( product.getId()))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }
}
