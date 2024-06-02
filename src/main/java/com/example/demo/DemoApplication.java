package com.example.demo;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication{
	private static final Logger logger
			= (Logger) LoggerFactory.getLogger( DemoApplication.class);

	public static void main(String[] args) {
		logger.info( "Example log from {}", DemoApplication.class.getSimpleName());

		SpringApplication.run( DemoApplication.class, args);
	}
}
