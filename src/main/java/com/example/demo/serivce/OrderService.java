package com.example.demo.serivce;

import com.example.demo.model.Order;
import com.example.demo.model.Product;

import java.util.List;

public interface OrderService {
    public List<Order> getAllOrders();

    Order getOrderById(long id);

    public Order createOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(long id);
}
