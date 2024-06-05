package com.example.demo.serivce;

import ch.qos.logback.classic.Logger;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger( ProductService.class);

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();
        productRepo.findAll().forEach( products::add);
        logger.info("{} Products Returned", products.size());
        return products;
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> productDb = productRepo.findById(id);

        if (productDb.isPresent()) {
            logger.info("Product {} Returned", productRepo.findById(id));
            return productDb.get();
        } else {
            logger.info("Product {} Not Exist", id);
            return null;
        }
    }

    @Override
    public Product createProduct(Product product) {
        logger.info("Product {} Created", product);
        return productRepo.save(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        Optional<Product> productDb = productRepo.findById( product.getId());

        if( productDb.isPresent()) {
            Product productUpdate = productDb.get();
            productUpdate.setName( product.getName());
            productUpdate.setPrice( product.getPrice());
            productRepo.save( productUpdate);
            logger.info("Factor {} Updated", product);
            return true;

        } else {
            logger.info("Factor {} Not Exist to Update", product.getId());
            return false;
        }
    }

    @Override
    public boolean deleteProduct(long id) {
        if( !productRepo.existsById( id)) {
            logger.info("Product {} Not Exist to Delete", id);
            return false;
        }

        productRepo.deleteById( id);
        logger.info("Product {} Deleted", id);
        return !productRepo.existsById( id);
    }
}
