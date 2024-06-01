package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
    private ProductRepo productRepo;

    private Product product;

    @BeforeEach
    public void setUp() {
        productRepo.deleteAll();
        product = new Product();
        product.setName( "pencil");
        product.setPrice( 3.45);
        productRepo.save( product);
    }

    @Test
    @Order(1)
    public void testGetAllProducts() throws Exception {
        mockMvc.perform( get("/products")
                        .contentType( MediaType.APPLICATION_JSON))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$[0].name", is( product.getName())))
                .andExpect( jsonPath("$[0].price", is( product.getPrice())));
    }

    @Test
    @Order(2)
    public void testGetProductById() throws Exception {
        JSONObject object = new JSONObject();
        object.put( "id", product.getId());

        mockMvc.perform( get("/product")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$.name", is( product.getName())))
                .andExpect( jsonPath("$.price", is( product.getPrice())));
    }

    @Test
    @Order(3)
    public void testCreateProduct() throws Exception {
        JSONObject object = new JSONObject();
        object.put( "name", "pen");
        object.put( "price", 4.56);

        mockMvc.perform( post("/create")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$.name", is( object.getString("name"))))
                .andExpect( jsonPath("$.price", is( object.getDouble("price"))));
    }

    @Test
    @Order(4)
    public void testUpdateProduct() throws Exception {
        Product product1 = productRepo.findByName( "pencil");

        JSONObject object = new JSONObject();
        object.put( "id", product1.getId());
        object.put( "name", "marker");
        object.put( "price", 5.67);

        mockMvc.perform( post("/update")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));
    }

    @Test
    @Order(5)
    public void testDeleteProduct() throws Exception {
        Product product1 = productRepo.findByName( "pencil");

        JSONObject object = new JSONObject();
        object.put( "id", product1.getId());

        mockMvc.perform( post("/delete")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));

        Optional<Product> deletedProduct = productRepo.findById( product.getId());
        assertEquals(Optional.empty(), deletedProduct);
    }
}
