package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;
import com.example.demo.serivce.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        productRepo.deleteAll();
        product = new Product( "pencil", 3.45);
        assertThat( product).isNotNull();

        productRepo.save( product);
        assertThat( product.getId()).isNotNull();
    }

    @Test
    @Order(3)
    void testGetAllProducts() {
        Product product1 = new Product("pen", 4.56);
        assertThat( product1).isNotNull();

        productService.createProduct( product1);
        assertThat( product1).isNotNull();

        List<Product> result = productService.getAllProducts();
        assertEquals(2, result.size());
    }

    @Test
    @Order(2)
    void testGetProductById() {
        Product result = productService.getProductById( product.getId());
        assertThat( result).isNotNull();
        assertEquals("pencil", result.getName());
    }

    @Test
    @Order(1)
    void testCreateProduct() {
        Product product = new Product("pencil", 3.45);
        assertThat( product).isNotNull();

        Product result = productService.createProduct( product);
        assertThat( result).isNotNull();

        assertEquals("pencil", result.getName());
    }

    @Test
    @Order(4)
    void testUpdateProduct() {
        Product product = new Product("pencil",3.45);
        assertThat( product).isNotNull();

        productService.createProduct( product);
        assertThat( product).isNotNull();
        assertThat( product.getId()).isNotNull();

        product.setPrice( 4.56);
        boolean ret = productService.updateProduct( product);
        assertEquals(true, ret);

        Product product1 = productService.getProductById( product.getId());
        assertEquals(4.56, product1.getPrice());
    }

    @Test
    @Order(5)
    void testDeleteProduct() {
        Product product = new Product("pencil",3.45);
        assertThat( product).isNotNull();

        productService.createProduct( product);
        assertThat( product).isNotNull();
        assertThat( product.getId()).isNotNull();

        boolean ret = productService.deleteProduct( product.getId());
        assertEquals(true, ret);
    }
}