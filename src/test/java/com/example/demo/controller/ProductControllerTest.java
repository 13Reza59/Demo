package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
        product = new Product();
        product.setName( "pencil");
        product.setPrice( 3.45);
        productRepository.save( product);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform( get("/products")
                        .contentType( MediaType.APPLICATION_JSON))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$[0].name", is( product.getName())))
                .andExpect( jsonPath("$[0].price", is( product.getPrice())));
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform( get("/products/{id}", product.getId())
                        .contentType( MediaType.APPLICATION_JSON))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$.name", is( product.getName())))
                .andExpect( jsonPath("$.price", is( product.getPrice())));
    }

    @Test
    public void testCreateProduct() throws Exception {
        mockMvc.perform( post("/products")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( "{\"name\": \"pen\", \"price\": 4.56}"))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$.name", is("pen")))
                .andExpect( jsonPath("$.price", is(4.56)));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        mockMvc.perform( put("/products")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( "{\"id\": 1, \"name\": \"marker\", \"price\": 5.67}"))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform( delete("/products/{id}", product.getId())
                        .contentType( MediaType.APPLICATION_JSON))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));

        Optional<Product> deletedProduct = productRepository.findById( product.getId());
        assertEquals(Optional.empty(), deletedProduct);
    }
}
