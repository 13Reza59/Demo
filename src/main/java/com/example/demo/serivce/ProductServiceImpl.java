package com.example.demo.serivce;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;

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
        productRepo.findAll().forEach( products::add);
        return products;
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> productDb = productRepo.findById(id);

        if (productDb.isPresent()) {
            return productDb.get();
        } else {
            return null;
        }
    }

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        if( !productRepo.existsById( product.getId()))
            return false;

        Optional<Product> productDb = productRepo.findById( product.getId());

        if( productDb.isPresent()) {
            Product productUpdate = productDb.get();
            productUpdate.setName( product.getName());
            productUpdate.setPrice( product.getPrice());
            productRepo.save( productUpdate);
            return true;

        } else
            return false;
    }

//    @Override
//    public boolean deleteProduct(long id) {
//        if( productRepository.existsById( id)) {
//            productRepository.deleteById( id);
//            return !productRepository.existsById( id);
//        } else
//            return false;
//    }

    @Override
    public boolean deleteProduct(long id) {
        productRepo.deleteById( id);
        return !productRepo.existsById( id);
    }
}
