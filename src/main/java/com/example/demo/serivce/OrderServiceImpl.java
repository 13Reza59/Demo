package com.example.demo.serivce;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepo.findAll().forEach( orders::add);
        return orders;
    }

    @Override
    public Order getOrderById(long id) {
        Optional<Order> orderDb = orderRepo.findById( id);

        if (orderDb.isPresent()) {
            return orderDb.get();
        } else {
            return null;
        }
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        if( !orderRepo.existsById( order.getId()))
            return false;

        Optional<Order> orderDb = orderRepo.findById( order.getId());

        if( orderDb.isPresent()) {
            Order orderUpdate = orderDb.get();
            orderUpdate.setDate( order.getDate());
            orderUpdate.setOwner( order.getOwner());
            orderRepo.save( orderUpdate);
            return true;

        } else
            return false;
    }

    @Override
    public boolean deleteOrder(long id) {
        orderRepo.deleteById( id);
        return !orderRepo.existsById( id);
    }
}
