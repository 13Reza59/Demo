package com.example.demo;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ProductRepo productRepo;

	@Test
	void contextLoads() throws Exception {
		assertThat( orderRepo).isNotNull();
		assertThat( productRepo).isNotNull();

		Order order = new Order("reza");

		Product product1 = new Product( "Table", 3.45);
		Product product2 = new Product( "Chair", 1.23);
		Product product3 = new Product( "Seat", 2.34);

		order.getProducts().add( product1);
		order.getProducts().add( product2);
		order.getProducts().add( product3);

		this.orderRepo.save( order);

	}

}
