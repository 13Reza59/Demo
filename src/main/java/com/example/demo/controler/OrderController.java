package com.example.demo.controler;

import com.example.demo.model.Order;
import com.example.demo.serivce.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController( OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(@RequestBody Order order) {
        return orderService.getAllOrders();
    }

    @GetMapping("/order")
    public Order getAllOrderById(@RequestBody Order order) {
        return orderService.getOrderById( order.getId());
    }

    @PostMapping("/order/add")
    public Order createOrder( @RequestBody Order order) {
        return orderService.createOrder( order);
    }

    @PostMapping("/order/update")
    public String updateOrder( @RequestBody Order order) {
        JSONObject output = new JSONObject();

        if( orderService.updateOrder( order))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }

    @PostMapping("/order/delete")
    public String deleteOrder( @RequestBody Order order) {
        JSONObject output = new JSONObject();

        if( orderService.deleteOrder( order.getId()))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }

}
