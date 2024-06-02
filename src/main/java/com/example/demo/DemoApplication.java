package com.example.demo;

import ch.qos.logback.classic.Logger;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepo;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.demo.model.ERole.*;

@SpringBootApplication
public class DemoApplication{
	private static final Logger logger
			= (Logger) LoggerFactory.getLogger( DemoApplication.class);

	@Autowired
    static RoleRepo roleRepo;

	public static void main(String[] args) {
		logger.info( "Example log from {}", DemoApplication.class.getSimpleName());

		Setup();

		SpringApplication.run( DemoApplication.class, args);
	}

	private static void Setup(){
		roleRepo.save( new Role( ROLE_USER));
//		roleRepo.save( new Role( ROLE_MODERATOR));
		roleRepo.save( new Role( ROLE_ADMIN));
	}
}
