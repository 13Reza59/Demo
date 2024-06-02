package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;
import com.example.demo.serivce.ProductService;
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

    @Test
    void testGetAllProducts() {
        Product product1 = new Product("pencil", 3.45);
        Product product2 = new Product("pen", 4.56);

        assertThat( product1).isNotNull();
        assertThat( product2).isNotNull();

        List<Product> products = Arrays.asList( product1, product2);
        productService.createProduct( product1);
        productService.createProduct( product2);

        assertThat( products).isNotNull();
        assertEquals(2, products.size());

        List<Product> result = productService.getAllProducts();
        assertEquals(2, result.size());
    }

    @Test
    void testGetProductById() {
        Product product = new Product("pencil", 3.45);
        assertThat( product).isNotNull();

        productService.createProduct( product);
        assertThat( product.getId()).isNotNull();

        Product result = productService.getProductById(1L);
        assertThat( result).isNotNull();

        assertEquals("pencil", result.getName());
    }

    @Test
    void testCreateProduct() {
        Product product = new Product("pencil", 3.45);
        assertThat( product).isNotNull();

        Product result = productService.createProduct( product);
        assertThat( result).isNotNull();

        assertEquals("pencil", result.getName());
    }

    @Test
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