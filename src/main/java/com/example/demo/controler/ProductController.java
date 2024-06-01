package com.example.demo.controler;

import com.example.demo.model.Product;
import com.example.demo.serivce.ProductService;
import com.example.demo.serivce.ProductServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product")
    public Product getAllProductById( @RequestBody Product product) {
        return productService.getProductById( product.getId());
    }

    @PostMapping("/create")
    public Product createProduct( @RequestBody Product product) {
        return productService.createProduct( product);
    }

    @PostMapping("/update")
    public String updateProduct( @RequestBody Product product) {
        JSONObject output = new JSONObject();

        if( productService.updateProduct( product))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }


//    @DeleteMapping("/{id}")
//    public void deleteProduct( @PathVariable long id) {
//        productService.deleteProduct( id);
//    }

    @PostMapping("/delete")
    public String deleteProduct( @RequestBody Product product) {
        JSONObject output = new JSONObject();

        if( productService.deleteProduct( product.getId()))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }
}
