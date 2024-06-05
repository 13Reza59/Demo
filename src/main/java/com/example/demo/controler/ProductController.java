package com.example.demo.controler;

import ch.qos.logback.classic.Logger;
import com.example.demo.model.Product;
import com.example.demo.serivce.ProductService;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger( ProductController.class);

    @Autowired
    private ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    @PreAuthorize( "hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<Product> getAllProducts() {
        logger.info("{} Factors Returned", productService.getAllProducts().size());
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Product getAllProductById( @PathVariable(value = "id") Long id) {
        logger.info("Factor {} Returned", productService.getProductById( id));
        return productService.getProductById( id);
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Product createProduct( @RequestBody Product product) {
        logger.info("Product {} Created", product);
        return productService.createProduct( product);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String updateProduct( @RequestBody Product product) {
        JSONObject output = new JSONObject();

        if( productService.updateProduct( product)) {
            output.put("result", "OK");
            logger.info("Product {} Updated", product);
        }else
            output.put( "result", "Not Found");

        return output.toString();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct( @RequestBody Product product) {
        JSONObject output = new JSONObject();

        if( productService.deleteProduct( product.getId())) {
            output.put("result", "OK");
            logger.info("Product {} Deleted", product);
        }else
            output.put( "result", "Not Found");

        return output.toString();
    }
}
