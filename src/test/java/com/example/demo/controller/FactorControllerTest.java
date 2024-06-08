package com.example.demo.controller;

import com.example.demo.model.Factor;
import com.example.demo.model.Product;
import com.example.demo.repository.FactorRepo;
import com.example.demo.repository.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FactorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FactorRepo factorRepo;

    private Factor factor;

    @Autowired
    private ProductRepo productRepo;

    private Product product;
    private Product product1;


    @BeforeEach
    public void setUp() throws Exception {
        //add a factor
        factorRepo.deleteAll();
        factor = new Factor("reza");
        assertThat(factor).isNotNull();

        //create products
        productRepo.deleteAll();
        product = new Product("pencil", 3.45);
        assertThat(product).isNotNull();

        product1 = new Product("pen", 4.56);
        assertThat(product1).isNotNull();

        factor.getProducts().add(product);
        factor.getProducts().add(product1);

        factorRepo.save(factor);
        assertThat(factor.getId()).isNotNull();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Order(3)
    public void testGetAllFactors() throws Exception {
        mockMvc.perform( get("/factor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].owner", is(factor.getOwner())));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Order(2)
    public void testGetFactorById() throws Exception {
        mockMvc.perform( get("/factor/{id}", factor.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner", is(factor.getOwner())));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Order(1)
    public void testCreateFactor() throws Exception {
        Factor factor1 = new Factor("masoud");
        factor1.getProducts().add(new Product("chair", 35.6));
        factor1.getProducts().add(new Product("table", 21.5));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(factor1);

        mockMvc.perform( post("/factor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value(factor1.getOwner()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Order(4)
    public void testUpdateFactor() throws Exception {
        Factor factor1 = factorRepo.findByOwner("reza");

        JSONObject object = new JSONObject();
        object.put("id", factor1.getId());
        object.put("owner", "marker");

        mockMvc.perform( put("/factor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("OK")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Order(5)
    public void testDeleteFactorAdmin() throws Exception {
        Factor factor1 = factorRepo.findByOwner("reza");

        JSONObject object = new JSONObject();
        object.put("id", factor1.getId());

        mockMvc.perform( delete("/factor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("OK")));

        Optional<Factor> deletedFactor = factorRepo.findById(factor.getId());
        assertEquals(Optional.empty(), deletedFactor);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "USER")
    @Order(6)
    public void testDeleteFactorUser() throws Exception {
        Factor factor1 = factorRepo.findByOwner("reza");

        JSONObject object = new JSONObject();
        object.put("id", factor1.getId());

        mockMvc.perform( delete("/factor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andExpect(status().isForbidden());
    }
}

