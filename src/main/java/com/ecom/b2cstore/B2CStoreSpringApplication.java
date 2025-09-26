package com.ecom.b2cstore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.entity.ShippingMethod;
import com.ecom.b2cstore.entity.Category;
import com.ecom.b2cstore.entity.CategoryAssignment;
import com.ecom.b2cstore.repository.CategoryRepository;
import com.ecom.b2cstore.repository.ProductRepository;
import com.ecom.b2cstore.repository.ShippingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class B2CStoreSpringApplication {

	public static void main(String[] args) {
		// http://localhost:8080/
		SpringApplication.run(B2CStoreSpringApplication.class, args);
	}

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ShippingRepository shippingMethodRepo;

	public List<Product> importProducts(String jsonFilePath) {
		try (InputStream iStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
			if (iStream == null) {
				System.err.println("File not found in resource: " + jsonFilePath);
				return null;
			}
			ObjectMapper mapper = new ObjectMapper();
			List<Product> products = Arrays.asList(mapper.readValue(iStream, Product[].class));
			for (Product product : products) {
				product.getInventory().setProduct(product);
				product.getPriceBook().setProduct(product);
				productRepo.save(product);
				System.out.println("Created product: " + product.toString());
			}
			return products;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Category> importCategories(String jsonFilePath) {
		try (InputStream iStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
			if (iStream == null) {
				System.err.println("File not found in resource: " + jsonFilePath);
				return null;
			}
			ObjectMapper mapper = new ObjectMapper();
			List<Category> categories = Arrays.asList(mapper.readValue(iStream, Category[].class));
			for (Category category : categories) {
				categoryRepo.save(category);
				System.out.println("Created category: " + category.toString());
			}
			return categories;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ShippingMethod> importShippingMethod(String jsonFilePath) {
		try (InputStream iStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
			if (iStream == null) {
				System.err.println("File not found in resource: " + jsonFilePath);
				return null;
			}
			ObjectMapper mapper = new ObjectMapper();
			List<ShippingMethod> shippingMethods = Arrays.asList(mapper.readValue(iStream, ShippingMethod[].class));
			for (ShippingMethod shippingMethod : shippingMethods) {
				shippingMethodRepo.save(shippingMethod);
				System.out.println("Created shipping method: " + shippingMethod.toString());
			}
			return shippingMethods;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void assignProductsToCategory(List<Category> categories, List<Product> products) {
		if (categories != null && products != null) {
			int productIndex = 0;
			for (Category category : categories) {
				for (int i = 0; i < 3 && productIndex < products.size(); i++, productIndex++) {
					CategoryAssignment assignment = new CategoryAssignment();
					assignment.setCategory(category);
					assignment.setProduct(products.get(productIndex));
					category.getAssignments().add(assignment);
				}
				categoryRepo.save(category);
			}
		}
	}

	@Bean
	CommandLineRunner demo() {
		return (args) -> {
			List<Product> products = importProducts("data/product.json");
			List<Category> categories = importCategories("data/category.json");
			assignProductsToCategory(categories, products);
			importShippingMethod("data/shipping.json");
		};
	}
}
