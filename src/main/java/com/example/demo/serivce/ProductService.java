package com.example.demo.serivce;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();

    public long getAllProductsCount();

    public Product createProduct(Product product);

    boolean updateProduct(Product product);

    Product getProductById(long productId);

    boolean deleteProduct(long id);

    // Other CRUD operations
}