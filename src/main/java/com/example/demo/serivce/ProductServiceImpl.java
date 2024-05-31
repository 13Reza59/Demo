package com.example.demo.serivce;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
//        List<Product> data = new ArrayList<>();
//        Product product = new Product(1L, "test", 2.34);
//        Product product2 = new Product(2L, "test2", 21.34);
//        data.add( product);
//        data.add( product2);
//        return data;

//        List<Product> data = productRepository.findAll();
//        return data;

        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach( products::add);
        return products;
    }

    @Override
    public long getAllProductsCount() {
        return productRepository.count();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        Optional<Product> productDb = productRepository.findById( product.getId());

        if( productDb.isPresent()) {
            Product productUpdate = productDb.get();
            productUpdate.setId( product.getId());
            productUpdate.setName( product.getName());
            productUpdate.setPrice( product.getPrice());
            productRepository.save( productUpdate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Product getProductById(long productId) {
        Optional<Product> productDb = productRepository.findById( productId);

        if (productDb.isPresent()) {
            return productDb.get();
        } else {
            return null;
        }
//        return productDb.get();
    }

    @Override
    public boolean deleteProduct(long id) {
        Optional<Product> productDb = productRepository.findById(id);

        if (productDb.isPresent()) {
            productRepository.delete( productDb.get());
            return true;
        } else {
            return false;
        }
    }

}
