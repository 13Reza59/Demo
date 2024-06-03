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
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        //add a product
        productRepo.deleteAll();
        product = new Product("pencil",3.45);
        assertThat( product).isNotNull();

        productRepo.save( product);
        assertThat( product.getId()).isNotNull();

        //signup an account
        ArrayList<String> roles = new ArrayList<>();
        roles.add( "\"ROLE_ADMIN\"");
        roles.add( "\"ROLE_USER\"");

        final Map<String, Object> signupBody = new HashMap<>();
        signupBody.put( "\"username\"", "\"admin\"");
        signupBody.put( "\"email\"", "\"ad@min.com\"");
        signupBody.put( "\"role\"", roles);
        signupBody.put( "\"password\"", "\"admin\"");

        mockMvc.perform( post("/auth/signup")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( signupBody.toString()))
                .andExpect( status().isOk());

        //login to that account
        final Map<String, Object> loginBody = new HashMap<>();
        loginBody.put( "\"username\"", "\"admin\"");
        loginBody.put( "\"password\"", "\"admin\"");

        MvcResult result = mockMvc.perform( post("/auth/login")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( loginBody.toString()))
                .andExpect( status().isOk())
                .andReturn();

        //save the token
        JSONObject resultObject = new JSONObject( result.getResponse().getContentAsString());
        token = resultObject.getString("accessToken");
        assertThat(token).isNotEmpty();
    }

    @Test
    @Order(3)
    public void testGetAllProducts() throws Exception {
        mockMvc.perform( get("/product/all")
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
                    .contentType( MediaType.APPLICATION_JSON))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$[0].name", is( product.getName())))
                .andExpect( jsonPath("$[0].price", is( product.getPrice())));
    }

    @Test
    @Order(2)
    public void testGetProductById() throws Exception {
        mockMvc.perform( get("/product/{id}", product.getId())
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
                    .contentType( MediaType.APPLICATION_JSON)
                )
                .andExpect( status().isOk())
                .andExpect( jsonPath("$.name", is( product.getName())))
                .andExpect( jsonPath("$.price", is( product.getPrice())));
    }

    @Test
    @Order(1)
    public void testCreateProduct() throws Exception {
        JSONObject object = new JSONObject();
        object.put( "name", "pen");
        object.put( "price", 4.56);

        mockMvc.perform( post("/product/add")
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
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

        mockMvc.perform( post("/product/update")
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
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

        mockMvc.perform( post("/product/delete")
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
                    .contentType( MediaType.APPLICATION_JSON)
                    .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));

        Optional<Product> deletedProduct = productRepo.findById( product.getId());
        assertEquals(Optional.empty(), deletedProduct);
    }
}
