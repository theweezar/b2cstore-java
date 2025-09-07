package com.ecom.b2cstore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.repo.CustomerRepository;
import com.ecom.b2cstore.repo.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class B2CStoreSpringApplication {

	public static void main(String[] args) {
		// http://localhost:8080/
		SpringApplication.run(B2CStoreSpringApplication.class, args);
		// ApplicationContext context =
		// SpringApplication.run(B2CStoreSpringApplication.class, args);
		// System.out.println("Bean count: " + context.getBeanDefinitionCount());
		// for (String name : context.getBeanDefinitionNames()) {
		// System.out.println(name);
		// }
	}

	@Autowired
	private ProductRepository productRepo;

	public void importProductsFromJson(String jsonFilePath) {
		try (InputStream iStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
			if (iStream == null) {
				System.err.println("File not found in resource: " + jsonFilePath);
				return;
			}
			ObjectMapper mapper = new ObjectMapper();
			List<Product> products = Arrays.asList(mapper.readValue(iStream, Product[].class));
			for (Product product : products) {
				product.getInventory().setProduct(product);
				product.getPriceBook().setProduct(product);
				productRepo.save(product);
				System.out.println("Created product: " + product.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Bean
	CommandLineRunner demo() {
		return (args) -> {
			importProductsFromJson("data/product.json");
		};
	}
}
