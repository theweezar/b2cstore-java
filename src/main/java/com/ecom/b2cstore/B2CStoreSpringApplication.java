package com.ecom.b2cstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.repo.CustomerRepository;

@SpringBootApplication
public class B2CStoreSpringApplication {

	public static void main(String[] args) {
		// http://localhost:8080/
		SpringApplication.run(B2CStoreSpringApplication.class, args);
		// ApplicationContext context = SpringApplication.run(B2CStoreSpringApplication.class, args);
		// System.out.println("Bean count: " + context.getBeanDefinitionCount());
		// for (String name : context.getBeanDefinitionNames()) {
		// System.out.println(name);
		// }
	}

	@Bean
	CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// for (int i = 0; i < 20; i++) {
			// 	Product p = new Product("p" + i, "Product " + i, "Description for product " + i, true,
			// 			"https://via.placeholder.com/150");
				
			// }
		};
	}
}
