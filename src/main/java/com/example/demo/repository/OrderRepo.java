package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepo extends JpaRepository<Order, Long> {
    public Order findByOwner(@Param("owner") String owner);
}
