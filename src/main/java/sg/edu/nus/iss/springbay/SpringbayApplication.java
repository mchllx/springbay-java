package sg.edu.nus.iss.springbay;


import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.springbay.models.Product;
import sg.edu.nus.iss.springbay.repositories.ProductRepo;
import sg.edu.nus.iss.springbay.services.ProductService;

@SpringBootApplication
public class SpringbayApplication implements CommandLineRunner {

	@Autowired
	private ProductService prodSvc;
	
	private Logger logger = Logger.getLogger(SpringbayApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(SpringbayApplication.class, args);
	}

	@Override
	public void run(String... args) {
		logger.info("Starting application: SpringBay");

		// this list is returning null
		List<Product> prodList = prodSvc.getAllProduct("products");
		logger.info("Saving each product into redis");

		// for (Product product: products) {
		// 	System.out.println("\t" + product);
		prodSvc.saveProducts(prodList);

		System.out.println("--------------------Loaded from Cache:--------------------" + prodList);
	}
}