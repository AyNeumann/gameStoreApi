package com.aymeric.gamestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GamestoreApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(GamestoreApplication.class, args);
	}

}
