package com.todo2.todo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Todo2Application {

	public static void main(String[] args) {
		System.out.println("SPRING_DATASOURCE_URL = " + System.getenv("SPRING_DATASOURCE_URL"));

		SpringApplication.run(Todo2Application.class, args);
	}

}
