package com.example.demo;

import com.example.demo.model.Factor;
import com.example.demo.model.Product;
import com.example.demo.repository.FactorRepo;
import com.example.demo.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private FactorRepo factorRepo;

	@Autowired
	private ProductRepo productRepo;

	@Test
	void contextLoads() throws Exception {
		assertThat(factorRepo).isNotNull();
		assertThat( productRepo).isNotNull();

		Factor factor = new Factor("reza");

		Product product1 = new Product( "Table", 3.45);
		Product product2 = new Product( "Chair", 1.23);
		Product product3 = new Product( "Seat", 2.34);

		factor.getProducts().add( product1);
		factor.getProducts().add( product2);
		factor.getProducts().add( product3);

		this.factorRepo.save( factor);
	}
}
