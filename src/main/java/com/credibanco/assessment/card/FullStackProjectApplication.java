package com.credibanco.assessment.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement	// Habilita la gestión de transacciones automaticamente
@SpringBootApplication
public class FullStackProjectApplication {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		SpringApplication.run(FullStackProjectApplication.class, args);
	}
}
