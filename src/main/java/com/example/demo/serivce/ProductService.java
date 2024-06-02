package com.example.demo.serivce;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();

    public Product createProduct(Product product);

    boolean updateProduct(Product product);

    Product getProductById(long id);

    boolean deleteProduct(long id);

    // Other CRUD operations
}