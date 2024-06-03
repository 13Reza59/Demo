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
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.model.ERole.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        factorRepo.deleteAll();
        factor = new Factor("reza");
        assertThat( factor).isNotNull();

        factorRepo.save(factor);
        assertThat( factor.getId()).isNotNull();

        //signup an account
        ArrayList<String> roles = new ArrayList<String>();
        roles.add( "\"ADMIN\"");
        roles.add( "\"USER\"");

        final Map<String, Object> signupBody = new HashMap<>();
        signupBody.put( "\"username\"", "\"admin\"");
        signupBody.put( "\"email\"", "\"ad@min.com\"");
        signupBody.put( "\"role\"", roles);
        signupBody.put( "\"password\"", "\"admin\"");

        mockMvc.perform( post("/signup")
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
                    .contentType( MediaType.APPLICATION_JSON)
                    .content( signupBody.toString()))
                .andExpect( status().isOk());

        //login to that account
        JSONObject signInRequestObject = new JSONObject();
        final Map<String, Object> loginBody = new HashMap<>();
        loginBody.put( "\"username\"", "\"admin\"");
        loginBody.put( "\"password\"", "\"admin\"");

        MvcResult result = mockMvc.perform( post("/signin")
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
                    .contentType( MediaType.APPLICATION_JSON)
                    .content( loginBody.toString()))
                .andExpect( status().isOk())
                .andReturn();

        JSONObject resultObject = new JSONObject( result.getResponse().getContentAsString());
        token = resultObject.getString("accessToken");
        assertNotEquals( "", token);
    }

    @Test
    @Order(3)
    public void testGetAllFactors() throws Exception {
        mockMvc.perform( get("/factor/all")
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
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
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
                    .contentType( MediaType.APPLICATION_JSON)
//                  .content( object.toString())
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
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
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
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
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
                    .header("AuthType", "Bearer")
                    .header("Authorization", token)
                    .contentType( MediaType.APPLICATION_JSON)
                    .content( object.toString()))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.result", is("OK")));

        Optional<Factor> deletedFactor = factorRepo.findById( factor.getId());
        assertEquals(Optional.empty(), deletedFactor);
    }
}
