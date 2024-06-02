package com.example.demo.service;

import com.example.demo.model.Factor;
import com.example.demo.repository.FactorRepo;
import com.example.demo.serivce.FactorService;
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
public class FactorServiceTest {

    @Autowired
    private FactorRepo factorRepo;

    @Autowired
    private FactorService factorService;

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
    void testGetAllProducts() {
        Factor factor1 = new Factor( "masoud");
        assertThat( factor1).isNotNull();

        factorService.createFactor( factor1);
        assertThat( factor1).isNotNull();

        List<Factor> result = factorService.getAllFactors();
        assertEquals(2, result.size());
    }

    @Test
    @Order(2)
    void testGetProductById() {
        Factor result = factorService.getFactorById( factor.getId());
        assertThat( result).isNotNull();
        assertEquals("reza", result.getOwner());
    }

    @Test
    @Order(1)
    void testCreateProduct() {
        Factor factor1 = new Factor( "masoud");
        assertThat( factor1).isNotNull();

        Factor result = factorService.createFactor( factor1);
        assertThat( result).isNotNull();

        assertEquals("masoud", result.getOwner());
    }

    @Test
    @Order(4)
    void testUpdateProduct() {
        factor.setOwner( "masoud");
        boolean ret = factorService.updateFactor( factor);
        assertEquals(true, ret);

        Factor product1 = factorService.getFactorById( factor.getId());
        assertEquals("masoud", product1.getOwner());
    }

    @Test
    @Order(5)
    void testDeleteProduct() {
        boolean ret = factorService.deleteFactor( factor.getId());
        assertEquals(true, ret);
    }
}