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

import java.util.Optional;

import static com.example.demo.model.ERole.ROLE_ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        productRepo.deleteAll();
        product = new Product("pencil",3.45);
        assertThat( product).isNotNull();

        productRepo.save( product);
        assertThat( product.getId()).isNotNull();

        JSONObject signUpRequestObject = new JSONObject();
        signUpRequestObject.put( "username", "admin");
        signUpRequestObject.put( "email", "ad@min.com");
        signUpRequestObject.put( "role", ROLE_ADMIN.toString());
        signUpRequestObject.put( "password", "admin");

        mockMvc.perform( post("/signup")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( signUpRequestObject.toString()))
                .andExpect( status().isOk());

        JSONObject signInRequestObject = new JSONObject();
        signInRequestObject.put( "username", "admin");
        signInRequestObject.put( "password", "admin");

        MvcResult result = mockMvc.perform( post("/signin")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( signInRequestObject.toString()))
                .andExpect( status().isOk())
                .andReturn();

        JSONObject resultObject = new JSONObject( result.getResponse().getContentAsString());
        token = resultObject.getString("token");
        assertNotEquals( "", token);
    }

    @Test
    @Order(3)
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
//        JSONObject object = new JSONObject();
//        object.put( "id", product.getId());

        mockMvc.perform( get("/product/{id}", product.getId())
                        .contentType( MediaType.APPLICATION_JSON)
//                        .content( object.toString())
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
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));

        Optional<Product> deletedProduct = productRepo.findById( product.getId());
        assertEquals(Optional.empty(), deletedProduct);
    }
}
