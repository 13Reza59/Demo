package com.example.demo.controller;

import com.example.demo.model.Factor;
import com.example.demo.repository.FactorRepo;
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

    @BeforeEach
    public void setUp() {
        factorRepo.deleteAll();
        factor = new Factor("reza");
        assertThat( factor).isNotNull();

        factorRepo.save(factor);
        assertThat( factor.getId()).isNotNull();
    }

    @Test
    @Order(3)
    public void testGetAllFactors() throws Exception {
        mockMvc.perform( get("/factors")
                        .contentType( MediaType.APPLICATION_JSON))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$[0].owner", is( factor.getOwner())));
    }

    @Test
    @Order(2)
    public void testGetFactorById() throws Exception {
//        JSONObject object = new JSONObject();
//        object.put( "id", product.getId());

        mockMvc.perform( get("/factor/{id}", factor.getId())
                        .contentType( MediaType.APPLICATION_JSON)
//                        .content( object.toString())
                )
                .andExpect( status().isOk())
                .andExpect( jsonPath("$.owner", is( factor.getOwner())));
    }

    @Test
    @Order(1)
    public void testCreateFactor() throws Exception {
        JSONObject object = new JSONObject();
        object.put( "owner", "reza");

        mockMvc.perform( post("/factor/add")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath("$.owner", is( object.getString("owner"))));
    }

    @Test
    @Order(4)
    public void testUpdateFactor() throws Exception {
        Factor factor1 = factorRepo.findByOwner( "reza");

        JSONObject object = new JSONObject();
        object.put( "id", factor1.getId());
        object.put( "owner", "marker");

        mockMvc.perform( post("/factor/update")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));
    }

    @Test
    @Order(5)
    public void testDeleteFactor() throws Exception {
        Factor factor1 = factorRepo.findByOwner( "reza");

        JSONObject object = new JSONObject();
        object.put( "id", factor1.getId());

        mockMvc.perform( post("/factor/delete")
                        .contentType( MediaType.APPLICATION_JSON)
                        .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));

        Optional<Factor> deletedFactor = factorRepo.findById( factor.getId());
        assertEquals(Optional.empty(), deletedFactor);
    }
}
