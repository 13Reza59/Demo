package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.ProductRepo;
import com.example.demo.serivce.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderService orderService;

    @Test
    void testGetAllProducts() {
        Order order1 = new Order( "reza");
        Order order2 = new Order( "masoud");

        assertThat( order1).isNotNull();
        assertThat( order2).isNotNull();

        List<Order> orders = Arrays.asList( order1, order2);
        orderService.createOrder( order1);
        orderService.createOrder( order2);

        assertThat( orders).isNotNull();
        assertEquals(2, orders.size());

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
    }

    @Test
    void testGetProductById() {
        Order order = new Order("reza");
        assertThat( order).isNotNull();

        orderService.createOrder( order);
        assertThat( order.getId()).isNotNull();

        Order result = orderService.getOrderById(1L);
        assertThat( result).isNotNull();

        assertEquals("reza", result.getOwner());
    }

    @Test
    void testCreateProduct() {
        Order order = new Order("reza");
        assertThat( order).isNotNull();

        Order result = orderService.createOrder( order);
        assertThat( result).isNotNull();

        assertEquals("reza", result.getOwner());
    }

    @Test
    void testUpdateProduct() {
        Order order = new Order("reza");
        assertThat( order).isNotNull();

        orderService.createOrder( order);
        assertThat( order).isNotNull();
        assertThat( order.getId()).isNotNull();

        order.setOwner( "masoud");
        boolean ret = orderService.updateOrder( order);
        assertEquals(true, ret);

        Order product1 = orderService.getOrderById( order.getId());
        assertEquals("masoud", product1.getOwner());
    }

    @Test
    void testDeleteProduct() {
        Order order = new Order("reza");
        assertThat( order).isNotNull();

        orderService.createOrder( order);
        assertThat( order).isNotNull();
        assertThat( order.getId()).isNotNull();

        boolean ret = orderService.deleteOrder( order.getId());
        assertEquals(true, ret);
    }
}