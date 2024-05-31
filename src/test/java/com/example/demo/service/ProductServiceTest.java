package com.example.demo.service;


import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.serivce.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith( MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setName( "pencil");
        product1.setPrice( 3.45);

        Product product2 = new Product();
        product2.setName( "pen");
        product2.setPrice( 4.56);

        List<Product> products = Arrays.asList( product1, product2);

        when( productRepository.findAll()).thenReturn( products);

        List<Product> result = productServiceImpl.getAllProducts();

        assertEquals(2, result.size());
        verify( productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId( 1L);
        product.setName( "pencil");
        product.setPrice( 3.45);

        when( productRepository.findById( 1L)).thenReturn( Optional.of( product));

        Product result = productServiceImpl.getProductById(1L);

        assertEquals("pencil", result.getName());
        verify( productRepository, times(1)).findById( 1L);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName( "pencil");
        product.setPrice( 3.45);

        when( productRepository.save( product)).thenReturn( product);

        Product result = productServiceImpl.createProduct( product);

        assertEquals("pencil", result.getName());
        verify( productRepository, times(1)).save( product);
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setName( "pencil");
        product.setPrice( 3.45);

        when( productRepository.save( product)).thenReturn( product);

        product.setPrice( 4.56);

        Product product1 = productRepository.save( product);

        Assertions.assertThat( product1.getPrice()).isEqualTo(4.56);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        doNothing().when( productRepository).deleteById( productId);
//        when( productRepository.deleteById( productId)).then( "{ result: \"OK\" }")));

        productServiceImpl.deleteProduct( productId);

        verify( productRepository).deleteById( productId);
    }
}